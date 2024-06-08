package tr.com.mermela.cybersecurityfinalproject.ui.devicelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tr.com.mermela.cybersecurityfinalproject.R
import tr.com.mermela.cybersecurityfinalproject.databinding.ItemDeviceBinding
import tr.com.mermela.cybersecurityfinalproject.domain.TargetInfo

/**
 *Created by Mert Melih Aytemur on 4/28/2024.
 */
class DeviceListAdapter(
    private val deviceList : List<TargetInfo>,
    private val onDetailButtonClick : (TargetInfo) -> Unit
) : RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemDeviceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(target : TargetInfo){
            with(binding){
                tvUsername.text = target.username

                target.isActive?.let {
                    btnDetail.isEnabled = it

                    val icon = if(it) R.drawable.ic_active else R.drawable.ic_passive

                    ivStatus.setImageResource(icon)
                }

                btnDetail.setOnClickListener {
                    onDetailButtonClick.invoke(target)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDeviceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int = deviceList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = deviceList[position]
        holder.bind(item)
    }
}