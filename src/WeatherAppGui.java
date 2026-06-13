import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui() {


        super("Weather App");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();

        getContentPane().setBackground(
                new Color(245,247,250)
        );

    }


    private ImageIcon loadImage(
            String resourcePath,
            int width,
            int height
    ){
        try{

            BufferedImage image =
                    ImageIO.read(
                            new File(resourcePath)
                    );

            Image scaledImage =
                    image.getScaledInstance(
                            width,
                            height,
                            Image.SCALE_SMOOTH
                    );

            return new ImageIcon(scaledImage);

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void addGuiComponents() {

        // ==========================
        // Search TextField
        // ==========================
        JTextField searchField = new JTextField() {

            @Override
            protected void paintComponent(Graphics g) {

                Graphics2D g2 = (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                g2.setColor(Color.WHITE);

                g2.fillRoundRect(
                        0,
                        0,
                        getWidth(),
                        getHeight(),
                        15,
                        15
                );

                g2.dispose();

                super.paintComponent(g);
            }
        };

        searchField.setOpaque(false);

        searchField.setBounds(
                20,
                20,
                340,
                40
        );

        searchField.setFont(
                new Font("Segoe UI", Font.PLAIN, 20)
        );

        searchField.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        10,
                        0,
                        10
                )
        );

        add(searchField);
        // ==========================
        // Search Button
        // ==========================

        JButton searchButton =
                new JButton(
                        loadImage("src/assets/search.png")
                );

        searchButton.setBounds(
                375,
                20,
                45,
                40
        );

        searchButton.setContentAreaFilled(false);

        searchButton.setFocusPainted(false);

        searchButton.setBorder(
                BorderFactory.createLineBorder(
                        new Color(150,150,150),
                        1
                )
        );
        searchButton.setMargin(new Insets(2,2,2,2));

        searchButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        add(searchButton);

        // ==========================
        // Weather Image
        // ==========================

        JLabel weatherConditionImage =
                new JLabel();

        weatherConditionImage.setBounds(
                10,
                100,
                220,
                220
        );

        weatherConditionImage.setIcon(
                loadImage(
                        "src/assets/cloudy.png",180,180
                )
        );

        add(weatherConditionImage);

        // ==========================
        // Temperature
        // ==========================

        JLabel temperatureText =
                new JLabel(
                        "- °C"
                );

        temperatureText.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        54
                )
        );

        temperatureText.setBounds(
                230,
                130,
                250,
                80
        );

        add(temperatureText);

        // ==========================
        // Condition
        // ==========================

        JLabel weatherConditionDesc =
                new JLabel(
                        "-"
                );

        weatherConditionDesc.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        32
                )
        );

        weatherConditionDesc.setBounds(
                230,
                210,
                180,
                50
        );

        add(weatherConditionDesc);

        // ==========================
        // City Label
        // ==========================

        JLabel locationIcon = new JLabel(
                loadImage("src/assets/location.png",14,14)
        );
        JLabel cityLabel = new JLabel("-");

        cityLabel.setFont(
                new Font("Segoe UI",
                        Font.PLAIN,
                        14)
        );

        cityLabel.setForeground(
                new Color(70, 90, 120)
        );
        locationIcon.setBounds(
                235,
                130,
                18,
                18
        );

        cityLabel.setBounds(
                265,
                130,
                200,
                30
        );
        JPanel locationPanel = new JPanel();
        locationPanel.setOpaque(false);
        locationPanel.setLayout(new FlowLayout(
                FlowLayout.LEFT,
                5,
                0
        ));
        locationPanel.add(locationIcon);
        locationPanel.add(cityLabel);
        locationPanel.setBounds(
                230,
                270,
                250,
                30
        );

        add(locationPanel);


        // ==========================
        // Update Label
        // ==========================

        JLabel updateLabel =
                new JLabel(
                        "-"
                );

        updateLabel.setForeground(
                Color.GRAY
        );

        updateLabel.setBounds(
                230,
                300,
                150,
                30
        );

        add(updateLabel);

        // ==========================
        // Humidity Card
        // ==========================

        RoundedPanel humidityPanel = new RoundedPanel(45);
        humidityPanel.setBounds(20, 420, 190, 110);
        humidityPanel.setLayout(null);
        humidityPanel.setBackground(Color.WHITE);

        humidityPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(230,230,230),
                        1
                )
        );

        add(humidityPanel);

        JLabel humidityImage =
                new JLabel(
                        loadImage(
                                "src/assets/humidity.png",40,40
                        )
                );

        humidityImage.setBounds(
                15,
                15,
                40,
                40
        );

        humidityPanel.add(
                humidityImage
        );


        JLabel humidityTitle =
                new JLabel("Humidity");

        humidityTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        humidityTitle.setBounds(70,15,100,20);

        humidityPanel.add(humidityTitle);

        JLabel humidityValue =
                new JLabel("- %");

        humidityValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        humidityValue.setBounds(70,35,100,30);

        humidityPanel.add(humidityValue);

        JProgressBar humidityBar =
                new JProgressBar(0,100);

        humidityBar.setValue(77);

        humidityBar.setBounds(
                15,
                72,
                150,
                8
        );

        humidityBar.setBorderPainted(false);
        humidityBar.setStringPainted(false);

// สีฟ้าเหมือนต้นแบบ
        humidityBar.setForeground(
                new Color(74, 120, 255)
        );

// สีพื้นหลังเทาอ่อน
        humidityBar.setBackground(
                new Color(225, 228, 232)
        );

        humidityPanel.add(humidityBar);


        // ==========================
        // Wind Card
        // ==========================

        RoundedPanel windPanel =
                new RoundedPanel(45);

        windPanel.setLayout(null);

        windPanel.setBounds(
                225,
                420,
                190,
                110
        );

        windPanel.setLayout(null);

        windPanel.setBackground(
                Color.WHITE
        );
        windPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(230,230,230),
                        1
                )
        );

        add(windPanel);

        JLabel windImage =
                new JLabel(
                        loadImage(
                                "src/assets/windspeed.png",40,40
                        )
                );

        windImage.setBounds(
                15,
                15,
                40,
                40
        );

        windPanel.add(
                windImage
        );

        JLabel windTitle =
                new JLabel("Wind");

        windTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        windTitle.setBounds(
                70,
                15,
                100,
                20
        );

        windPanel.add(windTitle);

        JLabel windValue =
                new JLabel("- km/h");

        windValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        windValue.setBounds(
                70,
                35,
                120,
                30
        );

        windPanel.add(windValue);


        // ==========================
        //  Wind Progress Bar
        // ==========================

        JProgressBar windBar =
                new JProgressBar(0,100);

        windBar.setValue(60);

        windBar.setBounds(
                15,
                72,
                150,
                8
        );

        windBar.setBorderPainted(false);
        windBar.setStringPainted(false);

        windBar.setForeground(
                new Color(74, 201, 185)
        );

        windBar.setBackground(
                new Color(225,228,232)
        );

        windPanel.add(windBar);



        // ==========================
        // Search Action
        // ==========================

        searchButton.addActionListener(e -> {

            String city =
                    searchField.getText();

            if(city.trim().isEmpty()){
                return;
            }

            weatherData =
                    WeatherApp.getWeatherData(
                            city
                    );

            if(weatherData == null){
                return;
            }

            String condition =
                    (String) weatherData.get(
                            "weather_condition"
                    );

            double temperature =
                    (double) weatherData.get(
                            "tempurature"
                    );

            long humidity =
                    (long) weatherData.get(
                            "humidity"
                    );

            double windspeed =
                    (double) weatherData.get(
                            "windspeed"
                    );

            cityLabel.setText(city);

            temperatureText.setText(
                    temperature + "°C"
            );

            weatherConditionDesc.setText(
                    condition
            );


            windValue.setText(
                    String.format("%.1f km/h", windspeed)
            );
            windBar.setValue(
                    Math.min((int)(windspeed * 5), 100)
            );

            humidityValue.setText(
                    humidity + "%"
            );


            humidityBar.setValue((int) humidity);

            switch(condition){

                case "Clear":
                    weatherConditionImage.setIcon(
                            loadImage(
                                    "src/assets/clear.png",180,160
                            )
                    );
                    break;

                case "Cloudy":

                    weatherConditionImage.setIcon(
                            loadImage("src/assets/cloudy.png",220,220)
                    );
                    break;

                case "Rain":
                    weatherConditionImage.setIcon(
                            loadImage(
                                    "src/assets/rain.png",180,180
                            )
                    );
                    break;

                case "Snow":
                    weatherConditionImage.setIcon(
                            loadImage(
                                    "src/assets/snow.png",20,20
                            )
                    );
                    break;
            }

        });

        // ==========================
        // Background
        // ==========================

        JLabel backgroundImage =
                new JLabel(
                        loadImage(
                                "src/assets/bk.png"
                        )
                );

        backgroundImage.setBounds(
                0,
                0,
                450,
                650
        );

        add(backgroundImage);

        getContentPane().setComponentZOrder(
                backgroundImage,
                getContentPane().getComponentCount() - 1
        );
    }

    private ImageIcon loadImage(String resourcePath) {
        try {
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Cannot Find resource");
        return null;
    }
}
