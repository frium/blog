package top.frium.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.frium.common.MyException;
import top.frium.common.StatusCodeEnum;
import top.frium.mapper.LinkMapper;
import top.frium.pojo.dto.LinkDTO;
import top.frium.pojo.entity.Link;
import top.frium.service.LinkService;

import java.time.LocalDateTime;
import java.util.List;

import static top.frium.context.CommonConstant.DATA_TIME_PATTERN;

/**
 * @date 2025-03-18 15:40:18
 * @description
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public boolean save(Link entity) {
        entity.setId(null);
        return super.save(entity);
    }

    @Override
    public void addLink(LinkDTO linkDTO) {
        if (linkDTO == null) throw new MyException(StatusCodeEnum.FAIL);
        Link link = new Link();
        link.setCreateTime(LocalDateTime.now().format(DATA_TIME_PATTERN));
        BeanUtils.copyProperties(linkDTO, link);
        save(link);
    }


    @Override
    public void deleteLink(List<Long> linksIds) {
        if (linksIds == null || linksIds.isEmpty()) throw new MyException(StatusCodeEnum.FAIL);
        removeByIds(linksIds);
    }

    @Override
    public void updateLink(LinkDTO linkDTO) {
        Link link = getById(linkDTO.getId());
        if (link == null) throw new MyException(StatusCodeEnum.FAIL);
        BeanUtils.copyProperties(linkDTO, link);
        updateById(link);
    }

    @Override
    public List<Link> getLinks() {
       return list();
    }
}
