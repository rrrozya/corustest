package corus.shortlinker.model.repository.impl;

import corus.shortlinker.model.database.DatabaseUtils;
import corus.shortlinker.model.domain.Link;

import corus.shortlinker.model.exception.RepositoryException;
import corus.shortlinker.model.repository.LinkRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

public class LinkRepositoryImpl implements LinkRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();


    @Override
    public void saveShortLink(Link link) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO corus.Link (link, shortLink, creationTime) VALUES (?, ?, NOW() + INTERVAL 10 minute )",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, link.getLink());
                statement.setString(2, link.getShortLink());
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        link.setId(generatedIdResultSet.getLong(1));
                        link.setCreationTime(findCreationTime(link.getId()));
                    } else {
                        throw new RepositoryException("Can't find id of saved User.");
                    }
                } else {
                    throw new RepositoryException("Can't save User.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save User.", e);
        }
    }

    @Override
    public Link findByShortLink(String shortLink) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM corus.Link WHERE shortLink=?")) {
                statement.setString(1, shortLink);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toLink(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    @Override
    public Link findByFullLink(String link) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM corus.Link WHERE link=?")) {
                statement.setString(1, link);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toLink(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by login.", e);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private Link toLink(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Link link = new Link();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                link.setId(resultSet.getLong(i));
            } else if ("link".equalsIgnoreCase(columnName)) {
                link.setLink(resultSet.getString(i));

            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                link.setCreationTime(resultSet.getTimestamp(i));
            } else if ("shortLink".equalsIgnoreCase(columnName)) {
                link.setShortLink(resultSet.getString(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return link;
    }


    private Date findCreationTime(long linkId) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM corus.Link WHERE id=?")) {
                statement.setLong(1, linkId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find User.creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.creationTime by id.", e);
        }
    }
    //TODO РЕАЛИЗОВАТЬ АВТОУДАЛЕНИЕ ИЗ БАЗЫ ДАННЫХ СПУСТЯ 10 МИНУТ ПОСЛЕ ДОБАВЛЕНИЯ ССЫЛКИ  (использовать EVENT (MySQL)

}
