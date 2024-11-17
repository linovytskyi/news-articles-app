import {Injectable} from "@angular/core";
import {EChartsOption} from "echarts";
import {PieChartData} from '../models/charts/pie-chart-data';


@Injectable({
  providedIn:'root'
})
export class EchartsAdapterService {
  public createOptionsForPieChart(name: string, legendNames: string[], data: PieChartData[]): EChartsOption {
    return  {
      title: {
        left: '50%',
        text: name,
        textAlign: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      legend: {
        align: 'auto',
        bottom: 10,
        data: legendNames
      },
      calculable: false,
      series: [
        {
          name: 'area',
          type: 'pie',
          radius: [30, 110],
          roseType: 'area',
          data: data
        },
      ],
    };
  }

  public createOptionsForSimpleChart(legendName: string[], xAxisData: any[], portfolioIncome: number[], portfolioCost: number[]): EChartsOption {
    return {
      legend: {
        data: ['Дохід від інвестування', 'Вартість портфелю'],
        align: 'auto',
        bottom: 10,
        selectorPosition: 'end'
      },
      tooltip: {},
      xAxis: {
        data: xAxisData,
        silent: false,
        splitLine: {
          show: false,
        },
      },
      yAxis: {},
      series: [
        {
          name: 'Дохід від інвестування',
          type: 'bar',
          data: portfolioIncome,
          animationDelay: idx => idx * 10,
        },
        {
          name: 'Вартість портфелю',
          type: 'bar',
          data: portfolioCost,
          animationDelay: idx => idx * 10 + 100,
        },
      ],
      animationEasing: 'elasticOut',
      animationDelayUpdate: idx => idx * 5,
    };
  }

  public createOptionsForDataSet(sourceData: any[][]): EChartsOption {
    return  {
      legend: {
        align: 'auto',
        bottom: 10,
        selectorPosition: 'end'
      },
      title: {
        left: '50%',
        textAlign: 'center'
      },
      tooltip: {},
      dataset: {
        // Provide a set of data.
        source: sourceData,
      },
      // Declare an x-axis (category axis).
      // The category map the first column in the dataset by default.
      xAxis: { type: 'category' },
      // Declare a y-axis (value axis).
      yAxis: {},
      // Declare several 'bar' series,
      // every series will auto-map to each column by default.
      series: [{ type: 'bar' }, {type: 'bar'}],
    };
  }
}
