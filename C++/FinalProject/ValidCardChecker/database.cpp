#include "database.h"
#include <QTextStream>

// Public
Database::Database() { data.clear(); }

Database::~Database() { data.clear(); }

Database::Database(const Database& copy) {
    this->data = copy.data;
}

Database& Database::operator=(const Database& rhs) {
    if(this!=&rhs)
        this->data = rhs.data;
    return *this;
}

void Database::load(const QString& path) {
    QString rawData = "";
    readFile(path, rawData);
    QTextStream store(&rawData, QIODevice::ReadOnly);
    while(!store.atEnd()) {
        int key = 0;
        QString value = "";
        QChar tempValue;
        store >> key;
        do {
            store >> tempValue;
            value += tempValue;
        }while(tempValue.toLatin1()!=0);
        data[key] = value;
    }
}

std::map<int, QString> Database::getData() const {
    return data;
}

// Private
void Database::readFile(const QString& path, QString& rawData) {
    char temp;
    QFile input(path);
    input.open(QFile::ReadOnly);
    while(!input.atEnd())
        if(input.getChar(&temp))
            rawData += temp;
    input.close();
}
