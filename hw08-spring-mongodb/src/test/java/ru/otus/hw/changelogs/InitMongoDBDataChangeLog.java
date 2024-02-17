package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author1;

    private Author author2;

    private Author author3;

    private Genre genre1;

    private Genre genre2;

    private Genre genre3;

    private Book book1;

    private Book book2;

    private Book book3;

    @ChangeSet(order = "000", id = "dropDB", author = "ralew", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "ralew", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        author1 = authorRepository.save(new Author("Author_1"));
        author2 = authorRepository.save(new Author("Author_2"));
        author3 = authorRepository.save(new Author("Author_3"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "ralew", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genre1 = genreRepository.save(new Genre("Genre_1"));
        genre2 = genreRepository.save(new Genre("Genre_2"));
        genre3 = genreRepository.save(new Genre("Genre_3"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "ralew", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        book1 = bookRepository.save(new Book("Book_1", author1, genre1));
        book2 = bookRepository.save(new Book("Book_2", author2, genre2));
        book3 = bookRepository.save(new Book("Book_3", author3, genre3));
    }

    @ChangeSet(order = "004", id = "initComments", author = "ralew", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        commentRepository.save(new Comment("Comment_1", book1));
        commentRepository.save(new Comment("Comment_2", book2));
        commentRepository.save(new Comment("Comment_3", book3));
        commentRepository.save(new Comment("Comment_4", book1));
        commentRepository.save(new Comment("Comment_5", book2));
        commentRepository.save(new Comment("Comment_6", book3));
    }
}
