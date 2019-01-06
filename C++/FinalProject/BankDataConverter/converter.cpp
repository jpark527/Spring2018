#include "converter.h"

// Public
Converter::Converter(const QString& rawData) {
    this->rawData = rawData;
    convertedData.clear();
}

Converter::~Converter() {
    rawData.clear();
    convertedData.clear();
}

Converter::Converter(const Converter& copy) {
    this->rawData = copy.rawData;
    this->convertedData = copy.convertedData;
}

Converter& Converter::operator=(const Converter& rhs) {
    if(this!=&rhs) {
        this->rawData = rhs.rawData;
        this->convertedData = rhs.convertedData;
    }
    return *this;
}

void Converter::convert() {
    QString nums="", data="";
    int currentIndex=0, numOfElements=0;
    std::deque<int> wordCounter;
    bool valid = true;
    findStartingIndexAndNumberOfItems(currentIndex, numOfElements);
    while(valid && (currentIndex < rawData.length())) {
        QString store = "";
        getTheHexNumbersForItems(currentIndex, numOfElements, wordCounter);
        getItems(currentIndex, wordCounter, store);
        getKeyAndValue(store);
    }
}

std::map<int, QString> Converter::getConvertedData() const {
    return convertedData;
}

// Private
void Converter::findStartingIndexAndNumberOfItems(int& startingIndex, int& numOfItems) {
    for(int i=0; i<rawData.length(); ++i) {
        if(rawData.at(i).toLatin1()=='X' && rawData.at(i+1).toLatin1()=='X' && rawData.at(i+2).toLatin1()=='X')
            ++numOfItems;
        if(rawData.at(i).toLatin1()==18 && rawData.at(i+1).toLatin1()==20) {
            startingIndex = i+2;
            break;
        }
    }
}

void Converter::getTheHexNumbersForItems(int& currentIndex, int& numOfElements, std::deque<int> &wordCounter) {
    for(int i=currentIndex; i<currentIndex+numOfElements; ++i)
        wordCounter.push_back(rawData.at(i).toLatin1());
    currentIndex += numOfElements;
}

void Converter::getItems(int& currentIndex, std::deque<int> &wordCounter, QString& storeItem) {
    while(wordCounter.size()) {
        QString tempStore = "";
        for(int i=currentIndex; i<currentIndex+wordCounter[0]; ++i)
            tempStore += rawData.at(i);
        currentIndex += wordCounter[0];
        wordCounter.pop_front();
        bool notAnElement = false;
        for(int i=0; i<tempStore.length(); ++i)
            if(!tempStore.at(i).toLatin1()) {
                notAnElement = true;
                break;
            }
        int newCount = 0;
        if(notAnElement)
            for(int i=0; i<tempStore.length(); ++i)
                newCount += tempStore.at(i).toLatin1();
        if(newCount)
            wordCounter.push_front(newCount);
        else
            storeItem += (tempStore+'\n');
        tempStore.clear();
    }
}

void Converter::getKeyAndValue(const QString& store) {
    int index = 0;
    QString key = "", value = "";
    for(int i=0; i<store.size(); ++i) {
        if(!store.at(i).isDigit())
            break;
        else {
            key += store.at(i);
            index = i+2;
        }
    }
    for(int i=index; i < store.size(); ++i)
        value += store.at(i);
    convertedData[key.toInt()] = value + QChar::fromLatin1(0);
}
