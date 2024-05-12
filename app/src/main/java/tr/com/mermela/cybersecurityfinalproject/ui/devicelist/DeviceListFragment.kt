package tr.com.mermela.cybersecurityfinalproject.ui.devicelist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import java.io.File
import java.io.FileOutputStream
import tr.com.mermela.cybersecurityfinalproject.R
import tr.com.mermela.cybersecurityfinalproject.databinding.FragmentDeviceListBinding
import tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter.DeviceListAdapter


class DeviceListFragment : Fragment() {

    private lateinit var binding: FragmentDeviceListBinding

    private val viewModel: DeviceListViewModel by viewModels()

    private lateinit var adapter : DeviceListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initListener() {
        with(binding){
            btnDownloadMalware.setOnClickListener {
                downloadFile()
            }
        }
    }

    private fun initRecyclerView() {
        val list = arrayListOf<String>("Mertm","elfm","usertest")
        adapter = DeviceListAdapter(list,::onDetailClick)

        binding.rvDevices.adapter = adapter
    }


    private fun onDetailClick(){

    }

    private fun downloadFile() {
        val assetManager = requireContext().assets
        val fileName = "yourfile.bat"

        val inputStream = assetManager.open(fileName)
        val outFile = File(requireContext().getExternalFilesDir(null), fileName)
        val outputStream = FileOutputStream(outFile)

        inputStream.copyTo(outputStream)

        inputStream.close()
        outputStream.close()

        Toast.makeText(context, "Dosya indirildi: ${outFile.absolutePath}", Toast.LENGTH_LONG).show()
    }

}