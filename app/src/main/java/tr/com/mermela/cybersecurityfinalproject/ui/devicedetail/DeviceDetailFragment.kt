package tr.com.mermela.cybersecurityfinalproject.ui.devicedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tr.com.mermela.cybersecurityfinalproject.databinding.FragmentDeviceDetailFagmentBinding
import tr.com.mermela.cybersecurityfinalproject.domain.TargetInfo

class DeviceDetailFragment : Fragment() {

    private lateinit var binding : FragmentDeviceDetailFagmentBinding

    private val viewModel : DeviceDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeviceDetailFagmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bundle'dan TargetInfo nesnesini çıkar
        val targetInfo = arguments?.getParcelable<TargetInfo>("targetInfo")

        // TargetInfo nesnesini kullanarak gerekli işlemleri yapın
        targetInfo?.let {
            Log.d("MY OBJECT TAG",targetInfo.toString())
        }

    }
}