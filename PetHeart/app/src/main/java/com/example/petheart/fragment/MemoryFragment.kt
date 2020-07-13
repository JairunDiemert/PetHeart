package com.example.petheart.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.petheart.R
import com.example.petheart.modeling.Memory
import com.example.petheart.modeling.MemoryDetailViewModel
import com.example.petheart.utility.getScaledBitmap
import java.io.File
import java.util.*

private const val ARG_MEMORY_ID = "memory_id"
private const val DIALOG_DATE = "DialogDate"
private const val REQUEST_DATE = 0
private const val REQUEST_PHOTO = 1
private const val DATE_FORMAT = "EEE, MMM, dd"

class MemoryFragment : Fragment(),
    DatePickerFragment.Callbacks {

    private lateinit var memory: Memory
    private lateinit var photoFile: File
    private lateinit var photoUri: Uri
    private lateinit var titleField: EditText
    private lateinit var descriptionField: EditText
    private lateinit var dateButton: Button
    private lateinit var photoButton: ImageButton
    private lateinit var photoView: ImageView
    private lateinit var favoritedSwitch: Switch
    private val memoryDetailViewModel: MemoryDetailViewModel by lazy {
        ViewModelProviders.of(this).get(MemoryDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memory = Memory()
        val memoryId: UUID = arguments?.getSerializable(ARG_MEMORY_ID) as UUID
        memoryDetailViewModel.loadMemory(memoryId)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_memory, container, false)

        titleField = view.findViewById(R.id.memory_title) as EditText
        descriptionField = view.findViewById(R.id.memory_description) as EditText
        dateButton = view.findViewById(R.id.memory_date) as Button
        favoritedSwitch = view.findViewById(R.id.memory_favorited) as Switch
        photoButton = view.findViewById(R.id.memory_camera) as ImageButton
        photoView = view.findViewById(R.id.memory_photo) as ImageView

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memoryDetailViewModel.memoryLiveData.observe(
            viewLifecycleOwner,
            Observer { memory ->
                memory?.let {
                    this.memory = memory
                    photoFile = memoryDetailViewModel.getPhotoFile(memory)
                    photoUri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.petheart.fileprovider",
                        photoFile
                    )
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                memory.title = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //This one too
            }
        }

        titleField.addTextChangedListener(titleWatcher)

        val descriptionWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                sequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                //This space intentionally left blank
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                memory.description = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                //This one too
            }
        }

        descriptionField.addTextChangedListener(descriptionWatcher)

        favoritedSwitch.apply {
            setOnCheckedChangeListener { _, isChecked ->
                memory.isFavorited = isChecked
            }
        }

        dateButton.setOnClickListener {

            DatePickerFragment.newInstance(memory.date)
                .apply {
                setTargetFragment(this@MemoryFragment,
                    REQUEST_DATE
                )
                show(this@MemoryFragment.requireFragmentManager(),
                    DIALOG_DATE
                )
            }
        }

        photoButton.apply {
            val packageManager: PackageManager = requireActivity().packageManager

            val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val resolvedActivity: ResolveInfo? =
                packageManager.resolveActivity(
                    captureImage,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            if (resolvedActivity == null) {
                isEnabled = false
            }

            setOnClickListener {
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

                val cameraActivities: List<ResolveInfo> =
                    packageManager.queryIntentActivities(
                        captureImage,
                        PackageManager.MATCH_DEFAULT_ONLY
                    )

                for (cameraActivity in cameraActivities) {
                    requireActivity().grantUriPermission(
                        cameraActivity.activityInfo.packageName,
                        photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }

                startActivityForResult(captureImage,
                    REQUEST_PHOTO
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        memoryDetailViewModel.saveMemory(memory)
    }

    override fun onDateSelected(date: Date) {
        memory.date = date
        updateUI()
    }

    private fun updateUI() {
        titleField.setText(memory.title)
        descriptionField.setText(memory.description)
        dateButton.text = memory.date.toString()
        favoritedSwitch.apply {
            isChecked = memory.isFavorited
            jumpDrawablesToCurrentState()
        }
        updatePhotoView()
    }

    private fun updatePhotoView() {
        if (photoFile.exists()) {
            val bitmap = getScaledBitmap(
                photoFile.path,
                requireActivity()
            )
            photoView.setImageBitmap(bitmap)
        } else {
            photoView.setImageDrawable(null)
        }
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().revokeUriPermission(
            photoUri,
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> return

            requestCode == REQUEST_PHOTO -> {
                requireActivity().revokeUriPermission(
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                updatePhotoView()
            }
        }
    }

    private fun getMemoryDetails(): String {
        val titleString = memory.title
        val dateString = DateFormat.format(DATE_FORMAT, memory.date).toString()
        val descriptionString = memory.description

        return getString(
            R.string.memory_details,
            titleString, dateString, descriptionString
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_memory, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.share_memory -> {
                Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getMemoryDetails())
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.memory_details_subject)
                    )
                }.also { intent ->
                    val chooserIntent =
                        Intent.createChooser(intent, getString(R.string.send_memory))
                    startActivity(chooserIntent)
                }

                true
            }

            R.id.delete_memory -> {

                memoryDetailViewModel.deleteMemory(memory)
                activity?.finish()

                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        fun newInstance(memoryId: UUID): MemoryFragment {
            val args = Bundle().apply {
                putSerializable(ARG_MEMORY_ID, memoryId)
            }
            return MemoryFragment().apply {
                arguments = args
            }
        }
    }
}