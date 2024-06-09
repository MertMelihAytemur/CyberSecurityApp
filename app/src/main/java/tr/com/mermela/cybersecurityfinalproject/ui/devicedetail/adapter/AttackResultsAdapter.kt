package tr.com.mermela.cybersecurityfinalproject.ui.devicedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tr.com.mermela.cybersecurityfinalproject.databinding.ItemAttackResultBinding
import tr.com.mermela.cybersecurityfinalproject.domain.AttackResult
import tr.com.mermela.cybersecurityfinalproject.util.ext.toTurkishDateFormat

class AttackResultsAdapter(
    private val attackResults: List<AttackResult>
) : RecyclerView.Adapter<AttackResultsAdapter.ViewHolder>() {

    private var expandedPosition = -1

    inner class ViewHolder(private val binding: ItemAttackResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attackResult: AttackResult, isExpanded: Boolean) {
            with(binding) {
                tvDate.text = attackResult.timestamp.toTurkishDateFormat()
                tvManufacturer.text = "Manufacturer: ${attackResult.systemInfo.Manufacturer}"
                tvModel.text = "Model: ${attackResult.systemInfo.Model}"
                tvNumberOfProcessor.text = "Number of Processors: ${attackResult.systemInfo.NumberOfProcessors}"
                tvTotalPhysicalMemory.text = "Total Physical Memory: ${attackResult.systemInfo.TotalPhysicalMemory} bytes"
                tvDiskModel.text = "Disk Model: ${attackResult.diskInfo.Model}"
                tvDiskSize.text = "Disk Size: ${attackResult.diskInfo.Size} bytes"
                // Display network info
                attackResult.networkInfo.forEachIndexed { index, networkInfo ->
                    when (index) {
                        0 -> {
                            tvNetworkDescription1.text = "Network 1: ${networkInfo.Description}"
                            tvNetworkIPAddress1.text = "IP Address: ${networkInfo.IPAddress}"
                            tvNetworkMACAddress1.text = "MAC Address: ${networkInfo.MACAddress}"
                        }
                        1 -> {
                            tvNetworkDescription2.text = "Network 2: ${networkInfo.Description}"
                            tvNetworkIPAddress2.text = "IP Address: ${networkInfo.IPAddress}"
                            tvNetworkMACAddress2.text = "MAC Address: ${networkInfo.MACAddress}"
                        }
                    }
                }
                tvOsCaption.text = "OS: ${attackResult.osInfo.Caption}"
                tvOsVersion.text = "Version: ${attackResult.osInfo.Version}"

                expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

                root.setOnClickListener {
                    expandedPosition = if (isExpanded) -1 else adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttackResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = attackResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        holder.bind(attackResults[position], isExpanded)
    }
}

