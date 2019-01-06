#ifndef GENERATOR_H
#define GENERATOR_H
#include <vector>
#include <map>
#include <QFile>
#include <QString>
#include <QTextStream>
#include <ctime>
#include <cstdlib>
#include <sstream>

// Fix database file path HERE!!
#define VISA_PATH "/Users/j/QtStuff/CS3A/visaConverted.dat"
#define MC_PATH "/Users/j/QtStuff/CS3A/mcConverted.dat"
#define AMEX_PATH "/Users/j/QtStuff/CS3A/amexConverted.dat"

using namespace std;

class Generator {
    map<QString, vector<QString>> myData;
    void readFromFile(const QString& path, QString& rawData);
    void loadData(const QString& path, map<QString, vector<QString>> &bankData);
    void generateHelper(QString& key);
    int checkHowManyItems(const QString& rawData) const;
    void divideDataInto(const int& numOfItems, QString& data, map<QString, vector<QString>>& bankData);
    void initialize();
    bool luhnAlgorithm(const QString& input) const;

public:
    Generator();
    QString generate(const QString& bankName, const QString &cardType);
    void getBankNames(vector<QString> &bankNames) const;
};

#endif // GENERATOR_H
