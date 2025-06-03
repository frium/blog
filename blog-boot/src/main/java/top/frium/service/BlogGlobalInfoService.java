package top.frium.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.frium.pojo.entity.BlogGlobalInfo;

/**
 * @date 2025-06-03 22:07:59
 * @description
 */
public interface BlogGlobalInfoService extends IService<BlogGlobalInfo> {
    void updateBlogGlobalInfo(BlogGlobalInfo blogGlobalInfo);

    BlogGlobalInfo getGlobalInfo();
}
