package top.xujiayao.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.xujiayao.bean.Article;
import top.xujiayao.bean.RespBean;
import top.xujiayao.service.ArticleService;
import top.xujiayao.utils.Util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Xujiayao
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	final ArticleService articleService;
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public RespBean addNewArticle(Article article) {
		int result = articleService.addNewArticle(article);
		if (result == 1) {
			return new RespBean("success", article.getId() + "");
		} else {
			return new RespBean("error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
		}
	}

	/**
	 * 上传图片
	 *
	 * @return 返回值为图片的地址
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
		StringBuilder url = new StringBuilder();
		String filePath = "/blogimg/" + sdf.format(new Date());
		String imgFolderPath = req.getServletContext().getRealPath(filePath);
		File imgFolder = new File(imgFolderPath);
		if (!imgFolder.exists()) {
			imgFolder.mkdirs();
		}
		url.append(req.getScheme())
			  .append("://")
			  .append(req.getServerName())
			  .append(":")
			  .append(req.getServerPort())
			  .append(req.getContextPath())
			  .append(filePath);
		String imgName = UUID.randomUUID() + "_" + Objects.requireNonNull(image.getOriginalFilename()).replaceAll(" ", "");
		try {
			IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
			url.append("/").append(imgName);
			return new RespBean("success", url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RespBean("error", "上传失败!");
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, Object> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
		int totalCount = articleService.getArticleCountByState(state, Util.getCurrentUser().getId(), keywords);
		List<Article> articles = articleService.getArticleByState(state, page, count, keywords);
		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", totalCount);
		map.put("articles", articles);
		return map;
	}

	@RequestMapping(value = "/{aid}", method = RequestMethod.GET)
	public Article getArticleById(@PathVariable Long aid) {
		return articleService.getArticleById(aid);
	}

	@RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
	public RespBean updateArticleState(Long[] aids, Integer state) {
		if (articleService.updateArticleState(aids, state) == aids.length) {
			return new RespBean("success", "删除成功!");
		}
		return new RespBean("error", "删除失败!");
	}

	@RequestMapping(value = "/restore", method = RequestMethod.PUT)
	public RespBean restoreArticle(Integer articleId) {
		if (articleService.restoreArticle(articleId) == 1) {
			return new RespBean("success", "还原成功!");
		}
		return new RespBean("error", "还原失败!");
	}
}
