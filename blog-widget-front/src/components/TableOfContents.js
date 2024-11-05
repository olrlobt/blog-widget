import React from 'react';
import styled from 'styled-components';

const TableOfContentsContainer = styled.div`
    background-color: #f9f9f9;
    padding: 20px;
    width: 200px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-right: 20px;
    margin-top: 15px;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;

    h2 {
        font-size: 1.5em;
        margin-bottom: 10px;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        margin-bottom: 8px;
    }

    a {
        color: #333;
        text-decoration: none;
        &:hover {
            text-decoration: underline;
        }
    }
`;

function TableOfContents({ items }) {
    return (
        <TableOfContentsContainer>
            <h2>목차</h2>
            <ul>
                {items.map((item, index) => (
                    <li key={index}>
                        <a href={item.link}>{item.title}</a>
                    </li>
                ))}
            </ul>
        </TableOfContentsContainer>
    );
}

export default TableOfContents;
