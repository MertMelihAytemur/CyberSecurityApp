package tr.com.mermela.cybersecurityfinalproject.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *Created by Mert Melih Aytemur on 5/13/2024.
 */

@Parcelize
data class AttackResult(
    val Manufacturer: String = "",
    val Model: String = "",
    val NumberOfProcessors: Int = 0,
    val TotalPhysicalMemory: Long = 0L
) : Parcelable
