package data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoTCharactersResponseDto(
    @SerialName("family")
    val family: String,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("id")
    val id: Int,
    @SerialName("image")
    val image: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("title")
    val title: String,
)
