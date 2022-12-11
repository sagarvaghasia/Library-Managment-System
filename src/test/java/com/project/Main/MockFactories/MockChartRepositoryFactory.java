package com.project.Main.MockFactories;

import com.project.Main.Models.BookTypeChart.Factories.IChartRepositoryFactory;
import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;
import com.project.Main.MockRepositories.MockChartRepository;

public class MockChartRepositoryFactory implements IChartRepositoryFactory {

    private static IChartRepositoryFactory chartRepositoryFactory = null;

    private MockChartRepositoryFactory() {
    }

    public static IChartRepositoryFactory instance() {
        if (chartRepositoryFactory == null) {
            chartRepositoryFactory = new MockChartRepositoryFactory();
        }
        return chartRepositoryFactory;
    }

    @Override
    public IChartRepository createChartRepository() {
        return new MockChartRepository();
    }
}
