#ifndef JARRAY_H
#define JARRAY_H

#include <iostream>
#include <string>
#include <sstream>
#include <fstream>
#include <cstdlib>
#include <cstring>
#include <vector>

using namespace std;

enum ERROR {OUT_OF_BOUND, BAD_SIZE, NULLPTR};

template<typename T>
class JArray {
    T* list;
    size_t mySize;

    void copy(const JArray<T>& other);
    void deleteAll();
public:
    JArray(const size_t& size=10);
    ~JArray();
    JArray(const JArray<T>& other);
    JArray& operator=(const JArray<T>& other);
    T operator[](size_t index) const;
    T& operator[](size_t index);

    void toNull(T* ptr, const size_t& index);
    void resize(const size_t& newSize);
    size_t size() const;
    void sort();

    template<typename U>
    friend ostream& operator<<(ostream& out, const JArray<U>& arr);
    template<typename U>
    friend istream& operator>>(istream& in, JArray<U>& arr);
};

// Public
template<typename T>
JArray<T>::JArray(const size_t& size) {
    if(size < 2)
        throw BAD_SIZE;
    list = new T[mySize=size];
}

template<>
JArray<char*>::JArray(const size_t& size) {
if(size < 2)
    throw BAD_SIZE;
    list = new char*[mySize=size];
}

template<typename T>
JArray<T>::~JArray() {
    deleteAll();
}

template<typename T>
JArray<T>::JArray(const JArray<T>& other) {
    copy(other);
}

template<typename T>
JArray<T>& JArray<T>::operator=(const JArray<T>& other) {
    if(this!=&other) {
        deleteAll();
        copy(other);
    }
    return *this;
}

template<typename T>
T JArray<T>::operator[](size_t index) const {
    if(index < 0 || index >= mySize)
        throw OUT_OF_BOUND;
    return list[index];
}

template<typename T>
T& JArray<T>::operator[](size_t index) {
    if(index < 0 || index >= mySize)
        throw OUT_OF_BOUND;
    return list[index];
}

template<typename T>
void JArray<T>::toNull(T* ptr, const size_t& index) {
    for(size_t i=0; i<index; ++i)
        ptr[i] = T();
}

template<typename T>
void JArray<T>::resize(const size_t& newSize) {
    if(newSize<2)
        throw BAD_SIZE;
    T* newList = new T[newSize];
    toNull(newList, newSize);
    for(size_t i=0; i<mySize; ++i)
        newList[i] = list[i];
    deleteAll();
    list = newList;
    mySize = newSize;
}

template<typename T>
size_t JArray<T>::size() const {
    return mySize;
}

template<typename T>
void JArray<T>::sort() {
    for (size_t i = 0; i < mySize - 1; i++) {
        size_t minIndex = i;
        for (size_t j = i+1; j < mySize; j++) {
            if(list[minIndex]>list[j])
                minIndex = j;
        }
        T temp = list[i];
        list[i] = list[minIndex];
        list[minIndex] = temp;
    }
}

template<>
void JArray<char*>::sort() {
    for (size_t i = 0; i < mySize; i++)
        if(list[i]==nullptr)
            throw NULLPTR;
    for (size_t i = 0; i < mySize - 1; i++) {
        size_t minIndex = i;
        for (size_t j = i+1; j < mySize; j++)
            if(list[minIndex][0]>list[j][0])
                minIndex = j;
        char* temp = list[i];
        list[i] = list[minIndex];
        list[minIndex] = temp;
    }
}

// Private
template<typename T>
void JArray<T>::copy(const JArray<T>& other) {
    list = new T[mySize = other.mySize];
    for(size_t i=0; i<mySize; ++i)
        list[i] = other.list[i];
}

template<typename T>
void JArray<T>::deleteAll() {
    toNull(list,mySize);
    delete[] list;
}

template<>
void JArray<char*>::deleteAll() {
    for(size_t i=0; i<mySize; ++i)
        if(!list[i])
            delete list[i];
    delete[] list;
}

// Friends
template<typename U>
ostream& operator<<(ostream& out, const JArray<U>& arr) {
    if(&out == &cout) {
        out<<"[ ";
        for(size_t i = 0; i < arr.mySize-1; ++i)
            out<<arr[i]<<", ";
        out<<arr[arr.mySize-1]<<" ]";
    } else {
        out<<"Size: "<<arr.mySize<<endl;
        for(size_t i = 0; i < arr.mySize; ++i)
            out<<arr[i]<<endl;
    }
    return out;
}

template<>
ostream& operator<<(ostream& out, const JArray<char*>& arr) {
    for (size_t i = 0; i < arr.mySize; i++)
        if(arr.list[i]==nullptr)
            throw NULLPTR;
    if(&out == &cout) {
        out<<"[ ";
        for(size_t i = 0; i < arr.mySize-1; ++i)
            out<<arr[i]<<", ";
        out<<arr[arr.mySize-1]<<" ]";
    } else {
        out<<"Size: "<<arr.mySize<<endl;
        for(size_t i = 0; i < arr.mySize; ++i)
            out<<arr[i]<<endl;
    }
    return out;
}

template<typename U>
istream& operator>>(istream& in, JArray<U>& arr) {
    string data;
    size_t index = 0;
    stringstream store;
    if(&in == &cin) {
        cout<<"Size: ";
        in>>arr.mySize;
        in.ignore(32767, '\n');
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(1) {
            cout<<"list["<<index<<"] = ";
            getline(in,data);
            if(data.empty())
                break;
            store.str("");
            store.clear();
            store << data;
            store >> arr[index];
            if(index+1 < arr.mySize)
                ++index;
            else
                break;
        }
    } else {
        getline(in,data);
        store<<data.substr(6);
        store>>arr.mySize;
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(in>>arr[index++]) {
            cout<<"Index is "<<index-1<<" "
                <<"Just read in "<<arr[index-1]<<endl;
        };
    }
    return in;
}

template<>
istream& operator>>(istream& in, JArray<string>& arr) {
    string data;
    size_t index = 0;
    stringstream store;
    if(&in == &cin) {
        cout<<"Size: ";
        in>>arr.mySize;
        in.ignore(32767, '\n');
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(1) {
            cout<<"list["<<index<<"] = ";
            getline(in,data);
            if(data.empty())
                break;
            arr[index] = data;
            if(index+1 < arr.mySize)
                ++index;
            else
                break;
        }
    } else {
        getline(in,data);
        store<<data.substr(6);
        store>>arr.mySize;
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(in>>arr[index++])
            cout<<"Index is "<<index-1<<" "<<"Just read in "<<arr[index-1]<<endl;
    }
    return in;
}

template<>
istream& operator>>(istream& in, JArray<char*>& arr) {
    string data;
    size_t index = 0;
    stringstream store;
    if(&in == &cin) {
        cout<<"Size: ";
        in>>arr.mySize;
        in.ignore(32767, '\n');
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(1) {
            cout<<"list["<<index<<"] = ";
            getline(in, data);
            if(data.empty())
                break;
            arr[index] = new char[data.length()+1];
            strcpy(arr[index], data.c_str());
            if(index+1 < arr.mySize)
                ++index;
            else
                break;
        }
    } else {
        getline(in,data);
        store<<data.substr(6);
        store>>arr.mySize;
        arr.resize(arr.mySize);
        arr.toNull(arr.list, arr.mySize);
        while(in>>arr[index++]) {
            cout<<"Index is "<<index-1<<" "
                <<"Just read in "<<arr[index-1]<<endl;
        };
    }
    return in;
}
#endif // JARRAY_H
