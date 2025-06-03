package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.frium.mapper.BlogGlobalInfoMapper;
import top.frium.pojo.entity.BlogGlobalInfo;
import top.frium.service.BlogGlobalInfoService;

/**
 * @date 2025-06-03 22:08:39
 * @description
 */
@Service
public class BlogGlobalInfoServiceImpl extends ServiceImpl<BlogGlobalInfoMapper, BlogGlobalInfo> implements BlogGlobalInfoService {

    @Override
    public void updateBlogGlobalInfo(BlogGlobalInfo blogGlobalInfo) {
        lambdaUpdate().eq(BlogGlobalInfo::getId, blogGlobalInfo.getId()).update(blogGlobalInfo);
    }

    @Override
    public BlogGlobalInfo getGlobalInfo() {
        return  lambdaQuery().one();
    }
}
