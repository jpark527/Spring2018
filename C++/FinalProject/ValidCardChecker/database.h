#ifndef DATABASE_H
#define DATABASE_H
#include <QString>
#include <map>
#include <QFile>

class Database {
    std::map<int, QString> data;
    void readFile(const QString& path, QString& rawData);
public:
    Database();
    ~Database();
    Database(const Database& copy);
    Database& operator=(const Database& rhs);

    void load(const QString& path);
    std::map<int, QString> getData() const;
};

#endif // DATABASE_H
