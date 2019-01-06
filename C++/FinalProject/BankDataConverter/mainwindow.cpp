#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow) {
    ui->setupUi(this);
    connectSignalsSlots();
}

MainWindow::~MainWindow() {
    delete ui;
}

void MainWindow::connectSignalsSlots() {
    connect(ui->exitButton, SIGNAL(pressed()), this, SLOT(close()));
    connect(ui->openFileButton,SIGNAL(pressed()), this,SLOT(openInputFile()));
    connect(ui->saveFileButton,SIGNAL(pressed()), this,SLOT(openSaveFile()));
}

void MainWindow::readFile(const QString &fileName) {
    char data;
    QFile input(fileName);
    input.open(QFile::ReadOnly);
    fileContents.clear();
    while(!input.atEnd())
        if(input.getChar(&data))
            fileContents += data;
    input.close();
    ui->fileContentsDisplay->clear();
    ui->fileContentsDisplay->append(fileContents);
}

void MainWindow::openSaveFile() {
    QString fileName = QFileDialog::getSaveFileName(NULL, "Save File", NULL , "*.dat");
    if(fileName.isNull())
        return;
    if (QFileInfo(fileName).suffix().isEmpty())
      fileName.append(".dat");
    saveFile(fileName);
}

void MainWindow::saveFile(const QString &fileName) {
    std::ofstream out(fileName.toStdString());
    if(fileContents.isEmpty()) {
        for(std::map<int, QString>::iterator it = bankData.begin(); it != bankData.end(); ++it)
            out << it->first << '\n' << it->second.toStdString();
    } else
        out << fileContents.toStdString();
    out.close();
}

void MainWindow::openInputFile() {
    QString fileName = QFileDialog::getOpenFileName(NULL, "Source File", NULL , "*.dat");
    if(fileName.isNull())
        return;
    if (QFileInfo(fileName).suffix().isEmpty())
      fileName.append(".dat");
    ui->fileName->setText(fileName);
    readFile(fileName);
}

void MainWindow::changeEvent(QEvent *e) {
    QMainWindow::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void MainWindow::on_convertButton_released() {
    Converter c(fileContents);
    c.convert();
    bankData = c.getConvertedData();
    printConvertedData();
}

void MainWindow::printConvertedData() {
    ui->fileContentsDisplay->clear();
    for(std::map<int, QString>::iterator it = bankData.begin(); it != bankData.end(); ++it)
        ui->fileContentsDisplay->append(QString::number(it->first) + '\n' + it->second);
    ui->fileContentsDisplay->repaint();

    if(bankData.size())
        fileContents.clear();
}
