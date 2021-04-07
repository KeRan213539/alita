/*
 * Copyright 2018-2021, ranke (213539@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package top.klw8.alita.service.base.mongo.beans;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;


/**
 * @ClassName: PageImpl
 * @Description: org.springframework.data.domain.Page 的实现, 解决spring自带实现在 Hession 反序列化时的问题
 * 部分接口未实现也不需要实现
 * @author klw
 * @date 2018年12月27日 上午11:56:03
 */
@Getter
@Setter
public class PageImpl<T> implements Page<T>, Serializable, Cloneable {

    private static final long serialVersionUID = 7566446708228465292L;

    /**
     * @author klw
     * @Fields page : 当前页码
     */
    private int page;
    
    /**
     * @author klw
     * @Fields limit : 每页条数
     */
    private int limit;
    
    /**
     * @author klw
     * @Fields total : 数据总量
     */
    private long total;
    
    /**
     * @author klw
     * @Fields content : 当前页数据
     */
    private List<T> content;
    
    public PageImpl() {}
    
    public PageImpl(List<T> content, long total) {
	this.total = total;
	this.content = content;
    }
    /*
     * <p>Title: getNumber</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#getNumber()
     */
    @Override
    public int getNumber() {
	return page;
    }

    /*
     * <p>Title: getSize</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#getSize()
     */
    @Override
    public int getSize() {
	return limit;
    }

    /*
     * <p>Title: getNumberOfElements</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#getNumberOfElements()
     */
    @Override
    public int getNumberOfElements() {
	return content.size();
    }

    /*
     * <p>Title: getContent</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#getContent()
     */
    @Override
    public List<T> getContent() {
	return content;
    }

    /*
     * <p>Title: hasContent</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#hasContent()
     */
    @Override
    public boolean hasContent() {
	return !content.isEmpty();
    }

    /*
     * <p>Title: getSort</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#getSort()
     */
    @Override
    public Sort getSort() {
	return null;
    }

    /*
     * <p>Title: isFirst</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#isFirst()
     */
    @Override
    public boolean isFirst() {
	return !hasPrevious();
    }

    /*
     * <p>Title: isLast</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#isLast()
     */
    @Override
    public boolean isLast() {
	return !hasNext();
    }

    /*
     * <p>Title: hasNext</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#hasNext()
     */
    @Override
    public boolean hasNext() {
	return getNumber() + 1 < getTotalPages();
    }

    /*
     * <p>Title: hasPrevious</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#hasPrevious()
     */
    @Override
    public boolean hasPrevious() {
	return getNumber() > 0;
    }

    /*
     * <p>Title: nextPageable</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#nextPageable()
     */
    @Override
    public Pageable nextPageable() {
	return null;
    }

    /*
     * <p>Title: previousPageable</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Slice#previousPageable()
     */
    @Override
    public Pageable previousPageable() {
	return null;
    }

    /*
     * <p>Title: iterator</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<T> iterator() {
	return content.iterator();
    }

    /*
     * <p>Title: getTotalPages</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Page#getTotalPages()
     */
    @Override
    public int getTotalPages() {
	return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    /*
     * <p>Title: getTotalElements</p>
     * @author klw
     * <p>Description: </p>
     * @return
     * @see org.springframework.data.domain.Page#getTotalElements()
     */
    @Override
    public long getTotalElements() {
	return total;
    }

    /*
     * <p>Title: map</p>
     * @author klw
     * <p>Description: </p>
     * @param converter
     * @return
     * @see org.springframework.data.domain.Page#map(java.util.function.Function)
     */
    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
	return null;
    }
    

}
