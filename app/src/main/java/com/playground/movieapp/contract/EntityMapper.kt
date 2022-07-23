package com.playground.movieapp.contract

/**
 * @Details EntityMapper: It helps to create the Model from Existing response source.
 * This way we can prevent any NPE due to service response changes.
 * @Author Roshan Bhagat
 */
interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel
}