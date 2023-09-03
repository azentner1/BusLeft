package com.andrej.busleft.base.mapper

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(model: DomainModel): Entity
}