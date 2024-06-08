package tr.com.mermela.cybersecurityfinalproject.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *Created by Mert Melih Aytemur on 5/12/2024.
 */
@Parcelize
data class TargetInfo(
    val username: String,
    val attackResult: AttackResult?,
    val attackStatus: String?,
    val isActive: Boolean?
) : Parcelable
