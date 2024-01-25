merge into authors(id, full_name)
values (1, 'Author_1'), (2, 'Author_2'), (3, 'Author_3');
alter table authors alter column id restart with 4;

merge into genres(id, name)
values (1, 'Genre_1'), (2, 'Genre_2'), (3, 'Genre_3');
alter table genres alter column id restart with 4;

merge into books(id, title, author_id, genre_id)
values (1, 'BookTitle_1', 1, 1), (2, 'BookTitle_2', 2, 2), (3, 'BookTitle_3', 3, 3);
alter table books alter column id restart with 4;