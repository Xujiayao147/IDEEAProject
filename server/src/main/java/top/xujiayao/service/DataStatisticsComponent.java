package top.xujiayao.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Xujiayao
 */
@Component
public class DataStatisticsComponent {

	final ArticleService articleService;

	public DataStatisticsComponent(ArticleService articleService) {
		this.articleService = articleService;
	}

	//每天执行一次，统计PV
	@Scheduled(cron = "1 0 0 * * ?")
	public void pvStatisticsPerDay() {
		articleService.pvStatisticsPerDay();
	}
}
