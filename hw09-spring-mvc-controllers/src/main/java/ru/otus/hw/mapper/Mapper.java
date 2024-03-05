package ru.otus.hw.mapper;

public interface Mapper<M, D> {
    M toDomainObject(D dto);

    D toDto(M model);
}
