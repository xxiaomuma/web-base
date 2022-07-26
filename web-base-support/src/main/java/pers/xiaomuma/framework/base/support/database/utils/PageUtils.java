package pers.xiaomuma.framework.base.support.database.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import pers.xiaomuma.framework.page.PageResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PageUtils {

    public static <T> boolean isEmpty(IPage<T> page) {
        return Objects.isNull(page) || CollUtil.isEmpty(page.getRecords());
    }

    public static <T> PageResult<T> restPage(Page<T> page) {
        return new PageResult<T>(
                page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getContent()
        );
    }

    public static <A, B> PageResult<B> restPage(Page<A> page, Callback<A, B> converter) {
        List<A> records = page.getContent();
        List<B> convertedRecord = null;
        if (CollectionUtils.isNotEmpty(records)) {
            convertedRecord = records.stream().map(converter::call).collect(Collectors.toList());
        }
        return new PageResult<>(
                page.getNumber(), page.getSize(),
                page.getTotalElements(),
                Optional.ofNullable(convertedRecord).orElse(new ArrayList<>())
        );
    }

    @FunctionalInterface
    public interface Callback<P, R> {
        R call(P param);
    }

}
