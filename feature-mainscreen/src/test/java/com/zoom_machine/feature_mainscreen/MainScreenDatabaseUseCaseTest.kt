package com.zoom_machine.feature_mainscreen

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.zoom_machine.api.services.MainScreenService
import com.zoom_machine.api.services.data.BestSeller
import com.zoom_machine.api.services.data.HotSales
import com.zoom_machine.database.DatabaseObject
import com.zoom_machine.database.mainscreen_model.MainScreenDao
import com.zoom_machine.feature_mainscreen.data.MainScreenRepositoryImpl
import com.zoom_machine.feature_mainscreen.domain.MainScreenDatabaseUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainScreenDatabaseUseCaseTest {
    private val mainScreenService: MainScreenService = mock()
    private lateinit var mainScreenDao: MainScreenDao
    private lateinit var database: DatabaseObject

    private lateinit var repository: MainScreenRepositoryImpl
    private  lateinit var useCase: MainScreenDatabaseUseCase

    private val hotSales = listOf(
        HotSales(0, true, "first", "first phone", "pictureOne", true),
        HotSales(1, false, "second", "second phone", "pictureTwo", true),
    )

    private val bestSales = listOf(
        BestSeller(0, true, "Best One", 1000, 800, "picOne"),
        BestSeller(1, false, "Best Two", 1200, 900, "picTwo")
    )

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, DatabaseObject::class.java).build()
        mainScreenDao = database.mainScreenDao()
        repository = MainScreenRepositoryImpl(mainScreenService, mainScreenDao)
        useCase = MainScreenDatabaseUseCase(repository)
    }

    @Test
    fun `insert list of HotSales in DB by saveHotSales()`(){
        runBlocking {
            val expected = hotSales
            useCase.saveHotSales(hotSales)
            val actual = repository.getHotSalesFromDatabase()
            Assert.assertEquals(expected,actual)
        }
    }

    @Test
    fun `insert list of BestSales in DB by saveBestSeller()`(){
        runBlocking {
            val expected = bestSales
            useCase.saveBestSeller(bestSales)
            val actual = repository.getBestSellerFromDatabase()
            Assert.assertEquals(expected,actual)
        }
    }

    @After
    fun closeDb() {
        database.close()
    }
}