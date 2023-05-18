package com.zoom_machine.feature_mainscreen

import com.zoom_machine.api.services.MainScreenService
import com.zoom_machine.api.services.data.BestSeller
import com.zoom_machine.api.services.data.HotSales
import com.zoom_machine.api.services.data.MainScreenResponse
import com.zoom_machine.database.mainscreen_model.MainScreenDao
import com.zoom_machine.feature_mainscreen.data.MainScreenRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.atLeastOnce
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MainScreenRepositoryImplTest {

    private val mainScreenService: MainScreenService = mock()
    private val mainScreenDao: MainScreenDao = mock()

    private val repository = MainScreenRepositoryImpl(mainScreenService, mainScreenDao)

    private val hotSales = listOf(
        HotSales(0, true, "first", "first phone", "pictureOne", true),
        HotSales(1, false, "second", "second phone", "pictureTwo", true),
    )

    private val bestSales = listOf(
        BestSeller(0, true, "Best One", 1000, 800, "picOne"),
        BestSeller(1, false, "Best Two", 1200, 900, "picTwo")
    )


    @Test
    fun `fun getContentPhones() should return object MainScreenResponse`() {
        val contentMainScreen = MainScreenResponse(hotSales = hotSales, bestSeller = bestSales)
        runBlocking {
            whenever(mainScreenService.getContentForMainScreen()).thenReturn(contentMainScreen)
            val expected = contentMainScreen
            val actual = repository.getContentPhones()
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `Was called method getContentForMainScreen() class MainScreenService `() {
        runBlocking {
            repository.getContentPhones()
            Mockito.verify(mainScreenService).getContentForMainScreen()
        }
    }

    @Test
    fun `Method getContentForMainScreen() was called atLeastOnce`() {
        runBlocking {
            repository.getContentPhones()
            Mockito.verify(mainScreenService, atLeastOnce()).getContentForMainScreen()
        }
    }

    @Test
    fun `fun getHotSalesFromDatabase() should return List of objects HotSales`(){
        runBlocking{
            whenever(mainScreenDao.getHotSalesList()).thenReturn(hotSales)
            val expected = hotSales
            val actual = repository.getHotSalesFromDatabase()
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun `fun getBestSellerFromDatabase() should return List of objects HotSales`(){
        runBlocking{
            whenever(mainScreenDao.getBestSellerList()).thenReturn(bestSales)
            val expected = bestSales
            val actual = repository.getBestSellerFromDatabase()
            Assert.assertEquals(expected, actual)
        }
    }
}