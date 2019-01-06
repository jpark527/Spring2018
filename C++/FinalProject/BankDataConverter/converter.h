#ifndef CONVERTER_H
#define CONVERTER_H
#include <QString>
#include <map>
#include <deque>

class Converter {
    QString rawData;
    std::map<int, QString> convertedData;

    void findStartingIndexAndNumberOfItems(int& startingIndex, int& numOfItems);
    void getTheHexNumbersForItems(int& currentIndex, int& numOfElements, std::deque<int> &wordCounter);
    void getItems(int& currentIndex, std::deque<int> &wordCounter, QString& storeItem);
    void getKeyAndValue(const QString& store);

public:
    Converter(const QString& rawData);
    ~Converter();
    Converter(const Converter& copy);
    Converter& operator=(const Converter& rhs);

    void convert();
    std::map<int, QString> getConvertedData() const;
};

#endif // CONVERTER_H
