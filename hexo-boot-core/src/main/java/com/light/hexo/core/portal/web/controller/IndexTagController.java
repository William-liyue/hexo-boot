package com.light.hexo.core.portal.web.controller;

import com.github.pagehelper.PageInfo;
import com.light.hexo.core.portal.model.MorePageInfo;
import com.light.hexo.mapper.model.Post;
import com.light.hexo.mapper.model.Tag;
import com.light.hexo.core.portal.common.CommonController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @Author MoonlightL
 * @ClassName: IndexTagController
 * @ProjectName hexo-boot
 * @Description: 标签控制器（首页）
 * @DateTime 2020/9/19 11:19
 */
@Controller
public class IndexTagController extends CommonController {

    /**
     * 标签
     * @param resultMap
     * @return
     */
    @GetMapping(value = {"tags", "tags/", "tags/index.html"})
    public String tags(Map<String, Object> resultMap) {
        List<Tag> tagList = this.tagService.listTagsByIndex();
        resultMap.put("tagList", tagList);
        // 兼容
        resultMap.put("count", tagList.size());
        resultMap.put("tagNum", tagList.size());
        return render("tags", false, resultMap);
    }

    @GetMapping(value = {"tags/{tagName}/", "tags/{tagName}/page/{pageNum}/"})
    public String tagsByName(@PathVariable String tagName, @PathVariable(value="pageNum", required = false) Integer pageNum, Map<String, Object> resultMap) {
        pageNum = pageNum == null ? 1 : pageNum;
        List<Post> postList = this.postService.listPostsByTagName(tagName, pageNum, PAGE_SIZE);
        // 此数据用于兼容老版本主题
        resultMap.put("pageInfo", new PageInfo<>(postList, PAGE_SIZE));
        resultMap.put("name", tagName);
        resultMap.put("type", "标签");
        resultMap.put("link", "tags");

        // 新分页数据
        resultMap.put("newPageInfo", new MorePageInfo(postList, PAGE_SIZE));

        return render("postList", false, resultMap);
    }

}
