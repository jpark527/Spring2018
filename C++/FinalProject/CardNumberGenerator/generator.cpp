#include "generator.h"

Generator::Generator() {
    srand(time(0));
    initialize();
}

void Generator::initialize() {
    map<QString, vector<QString>> visa, mc, amex;
    loadData(VISA_PATH, visa);
    loadData(MC_PATH, mc);
    loadData(AMEX_PATH, amex);
    for(auto& data : visa) {
        myData[data.first].push_back(data.second[0].toUpper());
        myData[data.first].push_back(data.second[1].toUpper());
    }
    for(auto& data : mc) {
        myData[data.first].push_back(data.second[0].toUpper());
        myData[data.first].push_back("N/A");
    }
    for(auto& data : amex) {
        myData[data.first].push_back("AMERICAN_EXPRESS");
        myData[data.first].push_back(data.second[2].toUpper());
    }
}

void Generator::getBankNames(vector<QString> &bankNames) const {
    for(auto& data : myData)
        bankNames.push_back(data.second[0]);
}

void Generator::readFromFile(const QString& path, QString& rawData) {
    char temp;
    QFile input(path);
    input.open(QFile::ReadOnly);
    while(!input.atEnd())
        if(input.getChar(&temp))
            rawData += temp;
    input.close();
}

int Generator::checkHowManyItems(const QString& rawData) const {
    int count = 0;
    for(int i=0; i<rawData.length(); ++i) {
        if(rawData.at(i)=='\n')
            ++count;
        if(rawData.at(i).toLatin1()==0)
            break;
    }
    return count;
}

void Generator::loadData(const QString& path, map<QString, vector<QString>>& bankData) {
    QString rawData = "";
    readFromFile(path, rawData);
    int numOfItems = checkHowManyItems(rawData);
    QTextStream store(&rawData, QIODevice::ReadOnly);
    while(!store.atEnd()) {
        QString data = "";
        QChar tempValue;
        do {
            store >> tempValue;
            data += tempValue;
        }while(tempValue.toLatin1()!=0);
        divideDataInto(numOfItems, data, bankData);
    }
}

void Generator::divideDataInto(const int& numOfItems, QString& data, map<QString, vector<QString>>& bankData) {
    QString key="";
    stringstream tempStore(data.toStdString());
    string temp;
    vector<QString> value;
    tempStore >> temp;
    key = QString::fromStdString(temp);
    for(int i=1; i<numOfItems; ++i) {
        temp = "";
        tempStore >> temp;
        value.push_back(QString::fromStdString(temp));
    }
    bankData[key] = value;
}

QString Generator::generate(const QString& bankName, const QString &cardType) {
    QString key = "", value = "";
    int count = 0;
    for(auto& data : myData)
        if(data.second[0]==bankName && data.second[1]==cardType) {
            key = data.first;
            ++count;
        }
    if(count>2) {
        count = rand()%count;
        for(auto& data : myData) {
            if(data.second[0]==bankName && data.second[1]==cardType)
                --count;
            if(!count && data.second[0]==bankName && data.second[1]==cardType)
                key = data.first;
        }
    }
    generateHelper(key);
    return key;
}

void Generator::generateHelper(QString& key) {
    if(key.isEmpty())
        return;
    QString lastDigit;
    for(int i=0; i<9; ++i)
        key += QString::number(rand()%10);
    do {
        lastDigit = QString::number(rand()%10);
    } while(!luhnAlgorithm(key + lastDigit));
    key += lastDigit;
}

bool Generator::luhnAlgorithm(const QString& input) const {
    int count=0;
    long number=input.trimmed().toLong(), modBy=10, sum=number % modBy, num=number / modBy;
    while(num && count < 16) {
        long digit = num % modBy;
        if(!(count%2))
            digit *= 2;
        if(digit>9)
            digit -= 9;
        sum += digit;
        num /= modBy;
        ++count;
    }
    return number && count==15 && !(sum % 10);
}
