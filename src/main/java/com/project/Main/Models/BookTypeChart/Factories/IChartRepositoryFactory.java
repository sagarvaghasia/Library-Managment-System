package com.project.Main.Models.BookTypeChart.Factories;

import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;

public interface IChartRepositoryFactory {

    public IChartRepository createChartRepository();
}
