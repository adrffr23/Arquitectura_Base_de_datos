
package com.universidad.cacheperformance.config;

import com.universidad.cacheperformance.model.Producto;
import com.universidad.cacheperformance.repository.ProductoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarProductos(
            ProductoRepository repository) {

        return args -> {

            if (repository.count() > 0) {
                System.out.println(
                        "Los productos ya estan cargados."
                );
                return;
            }

            String[] nombres = {
                    "Samsung Galaxy S24 Ultra",
                    "Apple iPhone 15 Pro Max",
                    "Xiaomi Redmi Note 13 Pro",
                    "Samsung Galaxy A55 5G",
                    "Apple iPhone 14",
                    "Motorola Edge 50 Pro",
                    "Google Pixel 8 Pro",
                    "OnePlus 12",
                    "Huawei Pura 70 Pro",
                    "POCO X6 Pro",
                    "ASUS ROG Strix G16",
                    "Lenovo Legion 5",
                    "HP Victus 15",
                    "Acer Nitro V 15",
                    "Dell XPS 13",
                    "MacBook Air M3",
                    "MacBook Pro M3",
                    "Lenovo IdeaPad Slim 3",
                    "ASUS VivoBook 15",
                    "HP Pavilion 15",
                    "PlayStation 5 Slim",
                    "Xbox Series X",
                    "Nintendo Switch OLED",
                    "Steam Deck OLED",
                    "PlayStation Portal",
                    "Xbox Wireless Controller",
                    "DualSense Wireless Controller",
                    "Logitech G29 Driving Force",
                    "Meta Quest 3",
                    "Nintendo Switch Pro Controller",
                    "Logitech G502 HERO",
                    "Razer DeathAdder V3",
                    "Logitech MX Master 3S",
                    "Razer BlackWidow V4",
                    "HyperX Alloy Origins",
                    "Corsair K70 RGB PRO",
                    "SteelSeries Apex Pro",
                    "Logitech G Pro X Headset",
                    "HyperX Cloud III",
                    "Razer Kraken V3",
                    "Sony WH-1000XM5",
                    "Apple AirPods Pro 2",
                    "Samsung Galaxy Buds2 Pro",
                    "JBL Tune 770NC",
                    "Bose QuietComfort Ultra",
                    "Sony SRS-XB100",
                    "JBL Flip 6",
                    "Marshall Emberton II",
                    "Anker Soundcore Motion+",
                    "Beats Studio Pro",
                    "Samsung Odyssey G5 27",
                    "LG UltraGear 27GP850",
                    "ASUS TUF Gaming VG27AQ",
                    "Dell UltraSharp U2723QE",
                    "AOC 24G2SP",
                    "Samsung 990 PRO SSD 1TB",
                    "Kingston NV2 SSD 1TB",
                    "Crucial P3 Plus SSD 2TB",
                    "WD Black SN850X 2TB",
                    "Seagate BarraCuda HDD 2TB"
            };

            List<Producto> productos = new ArrayList<>();

            for (int i = 0; i < 10000; i++) {

                String nombre = nombres[i % nombres.length];

                BigDecimal precio = BigDecimal.valueOf(
                        50 + (i % 1500)
                );

                int stock = 1 + (i % 100);

                productos.add(
                        new Producto(nombre, precio, stock)
                );
            }

            repository.saveAll(productos);

            System.out.println(
                    "Se cargaron 10000 productos correctamente."
            );
        };
    }
}
