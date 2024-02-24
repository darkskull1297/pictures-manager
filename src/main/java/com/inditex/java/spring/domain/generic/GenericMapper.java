package com.inditex.java.spring.domain.generic;

import java.util.List;

public interface GenericMapper<S, T> {
    /**
     *Convert to Domain Model
     * @param s it is an Domain Model
     * @return an Domain Model
     */
    T toDTO(S s);

    /**
     *Convert to Entity
     * @param t it is an Entity
     * @return an Entity
     */
    S toEntity(T t);

    /**
     *Convert to a List of Entities
     * @param tList is a List of Entities
     * @return a List of Entities
     */
    List<S> toEntities(List<T> tList);

    /**
     *Convert to a List of Domain Model
     * @param sList a List of Domain Model
     * @return a List of Domain Model
     */
    List<T> toDTOList(List<S> sList);
}
