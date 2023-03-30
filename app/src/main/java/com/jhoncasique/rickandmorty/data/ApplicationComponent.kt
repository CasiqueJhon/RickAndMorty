package com.jhoncasique.rickandmorty.data

import dagger.Component
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@DefineComponent(parent = SingletonComponent::class)
@Component(modules = [NetworkModule::class])
@Singleton
interface ApplicationComponent