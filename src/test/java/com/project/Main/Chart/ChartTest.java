package com.project.Main.Chart;

import com.project.Main.Models.BookTypeChart.Factories.ChartFactory;
import com.project.Main.Models.BookTypeChart.Factories.IChartRepositoryFactory;
import com.project.Main.MockFactories.MockChartRepositoryFactory;
import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChartTest {

    private static IChartRepositoryFactory mockChartRepositoryFactory = MockChartRepositoryFactory.instance();
    private static IBookTypeChart bookTypeChart = ChartFactory.instance().createBookTypeChart(
            mockChartRepositoryFactory.createChartRepository());

    private static IRevenueChart revenueChart = ChartFactory.instance().createRevenueChart(
            mockChartRepositoryFactory.createChartRepository());

    @Test
    public void getTypeOfBookDataTest() {
        LoadState dataLoadStatus = bookTypeChart.getTypeOfBookIssuedData();
        assertEquals(true, dataLoadStatus.isSuccess());
    }

    @Test
    public void getRevenueData() {
        LoadState dataLoadState = revenueChart.getMonthlyRevenueData();
        assertEquals(true, dataLoadState.isSuccess());
    }

}
