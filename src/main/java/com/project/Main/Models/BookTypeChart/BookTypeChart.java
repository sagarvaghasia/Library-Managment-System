package com.project.Main.Models.BookTypeChart;

import com.project.Main.Models.RevenueChart.Repositories.IChartRepository;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Models.CommonState.LoadState.LoadSQLFailureState;
import com.project.Main.Models.CommonState.LoadState.LoadState;
import com.project.Main.Models.CommonState.LoadState.LoadSuccessState;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookTypeChart implements IBookTypeChart {
    @Getter @Setter private List<Map.Entry<String, Integer>> chartData;

    @Getter @Setter private IChartRepository chartRepository;

    public BookTypeChart(IChartRepository chartRepository) {
        this.chartData = new ArrayList<>();
        this.chartRepository = chartRepository;
    }

    public BookTypeChart(List<Map.Entry<String, Integer>> chartData, IChartRepository chartRepository) {
        this.chartData = chartData;
        this.chartRepository = chartRepository;
    }

    public LoadState getTypeOfBookIssuedData() {

        Map.Entry<IBookTypeChart, DatabaseState> chartDataResponse = chartRepository.getTypeOfBookIssuedData();

        if (chartDataResponse.getValue().isSuccess()) {
            IBookTypeChart chart = chartDataResponse.getKey();
            this.setChartData(chart.getChartData());
            return new LoadSuccessState();
        }

        return new LoadSQLFailureState();
    }
}
