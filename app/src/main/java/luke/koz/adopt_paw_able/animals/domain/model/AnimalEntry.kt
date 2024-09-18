package luke.koz.adopt_paw_able.animals.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimalEntry(
    @SerialName(value = "id") val id : Int,
    val category : Int,
    val breed : Int?,
    val age : Int?,
    val gender : Boolean?,
    @SerialName(value = "is_neutered") val isNeutered : Boolean?,
    val location : String?,
    val weight : Float?,
    @SerialName(value = "required_maintenance") val requiredMaintenance : Int?,
    @SerialName(value = "house_trained") val isHouseTrained : Boolean?,
    @SerialName(value = "vaccination") val isVaccinated : Int?,
    @SerialName(value = "brief_bio") val briefBio : String?,
    @SerialName(value = "is_declawed") val isDeclawed : Int?,
    @SerialName(value = "is_free") val isFree : Boolean,
    val price : Float?,
    @SerialName(value = "photo_url") val photoURL : String?,
)
