# Rent A Car

Bu proje, Spring Boot kullanarak geliştirilmiş bir Rent A Car uygulamasıdır.
Müşterilerin araç kiralayabileceği basit bir otomobil kiralama sistemidir.
Proje, Spring JPA kullanarak PostgreSQL veritabanını yönetir ve aynı zamanda unit testler ile güvenilir bir yapı sunar.


## Teknolojiler ve Araçlar

```
- Spring Boot
- Spring JPA
- PostgreSQL
- Unit Test
- Maven
```

## Model ve İlişkiler

```
Proje, aşağıdaki ana modeller ve aralarındaki ilişkilerle yapılandırılmıştır:

Brand: Otomobil markalarını temsil eder.
Model: Her marka için mevcut olan otomobil modellerini temsil eder.
Car: Rent A Car sistemindeki araçları temsil eder ve her biri bir model ile ilişkilidir.
Customer: Müşteri bilgilerini temsil eder.
Rental: Araç kiralama işlemlerini temsil eder ve her biri bir müşteri ve bir araç ile ilişkilidir.
```

## Çalıştırma

```
Projeyi çalıştırmak için aşağıdaki adımları takip edin:

1. Proje klasörünü yerel makinenize klonlayın.
2. PostgreSQL veritabanı oluşturun ve bağlantı bilgilerini application.properties dosyasında yapılandırın.
3. RentACarApplication'a sağ tıklayıp 'Run' ederek çalıştırabilirsiniz.

Uygulama başarıyla başladıktan sonra, http://localhost:8080 adresinden Rent A Car uygulamasına erişebilirsiniz.
```
