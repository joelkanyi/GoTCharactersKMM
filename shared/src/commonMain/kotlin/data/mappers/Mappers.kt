package data.mappers

import data.network.dto.GoTCharactersResponseDto
import domain.model.GoTCharacter

fun GoTCharactersResponseDto.toDomain() = GoTCharacter(
    family = family,
    firstName = firstName,
    fullName = fullName,
    id = id,
    image = image,
    imageUrl = imageUrl,
    lastName = lastName,
    title = title,
)
