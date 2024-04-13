import React from 'react'

const styles = {
    booksTable: {
        border: "1px solid steelblue",
        width: "300px",
        borderCollapse: "collapse",
    },

    booksTableItem: {
        padding: "5px",
        border: "1px solid steelblue"
    }
}

const Header = (props) => (
    <h1>{props.title}</h1>
);

export default class App extends React.Component {

     constructor() {
        super();
        this.state = {books: []};
    }

     componentDidMount() {
        fetch('api/book')
            .then(response => response.json())
            .then(books => this.setState({books}));
    }

    render() {
        return (
            <React.Fragment>
                <Header title={'Books'}/>
                <table style={styles.booksTable}>
                    <thead>
                    <tr style={styles.booksTableItem}>
                        <th style={styles.booksTableItem}>ID</th>
                        <th style={styles.booksTableItem}>title</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.books.map((book, i) => (
                            <tr style={styles.booksTableItem} key={i}>
                                <td style={styles.booksTableItem}>{book.id}</td>
                                <td style={styles.booksTableItem}>{book.title}</td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        )
    }
};