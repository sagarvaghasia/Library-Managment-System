package com.project.Main.Models.BookTypeChart.Factories;

import com.project.Main.Models.RevenueChart.Repositories.ChartRepository;
import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;

public class ChartRepositoryFactory implements IChartRepositoryFactory {

    private static IChartRepositoryFactory chartRepositoryFactory;

    private ChartRepositoryFactory() {
    }

    public static IChartRepositoryFactory instance() {
        if (chartRepositoryFactory == null) {
            chartRepositoryFactory = new ChartRepositoryFactory();
        }
        return chartRepositoryFactory;
    }

    @Override
    public IChartRepository createChartRepository() {
        return new ChartRepository();
    }
}
