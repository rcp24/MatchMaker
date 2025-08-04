package com.shadi.matchmate.data.mapper
import com.shadi.matchmate.data.local.ProfileMatchEntity
import com.shadi.matchmate.data.remote.dto.PersonProfileDto
import com.shadi.matchmate.data.remote.dto.Result
import com.shadi.matchmate.domain.model.ProfileMatch


fun Result.toProfileMatch(): ProfileMatch {
    return ProfileMatch(
        userId = login.uuid,
        name = "${name.first} ${name.last}",
        profilePicUrl = picture.large,
        address = "${location.street.number}, ${location.street.name}"
    )
}
fun PersonProfileDto.toProfileMatches(): List<ProfileMatch> {
    return results.map { it.toProfileMatch() }
}
fun PersonProfileDto.toEntity(): List<ProfileMatchEntity> {
    return results.map { it.toEntity() }
}
fun Result.toEntity() =
    ProfileMatchEntity(
        userId = login.uuid,
        name = "${name.first} ${name.last}",
        profilePicUrl = picture.large,
        address = "${location.street.number}, ${location.street.name}",
    )
fun ProfileMatchEntity.toProfileMatch(): ProfileMatch{
    return ProfileMatch(
        userId = userId,
        name = name,
        profilePicUrl = profilePicUrl,
        address = address,
        status=status
    )
}