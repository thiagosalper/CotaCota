package br.com.salperdev.cotacota.di

import android.content.Context
import androidx.room.Room
import br.com.salperdev.cotacota.domain.AddFornecedorUseCase
import br.com.salperdev.cotacota.data.repository.DefaultFornecedorRepository
import br.com.salperdev.cotacota.data.repository.FornecedorRepository
import br.com.salperdev.cotacota.data.source.local.FornecedoresDatabase
import br.com.salperdev.cotacota.data.source.local.LocalDataSource
import br.com.salperdev.cotacota.data.source.local.RoomLocalDataSource
import br.com.salperdev.cotacota.data.source.remote.ApiRemoteDataSource
import br.com.salperdev.cotacota.data.source.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddFornecedorUseCase(
        repository: FornecedorRepository
    ): AddFornecedorUseCase {
        return AddFornecedorUseCase(repository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideFornecedoresRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FornecedorRepository {
        return DefaultFornecedorRepository(localDataSource, remoteDataSource, ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = ApiRemoteDataSource()

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: FornecedoresDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return RoomLocalDataSource(database.fornecedoresDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FornecedoresDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FornecedoresDatabase::class.java,
            "Fornecedores.db"
        ).build()
    }
}