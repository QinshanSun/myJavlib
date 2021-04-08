package com.shan.tech.javlib.mapper;

import com.shan.tech.javlib.pojo.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface VideoMapper {

    Optional<Video> findById(Long id);
}
