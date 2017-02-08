/**
 * Created by Ecoste on 2/6/2017.
 */
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

//this class should be a singleton
//This is pretty bad code in this class.
public class XML {
    String filepath = "C:\\Users\\Ecoste\\IdeaProjects\\MicroSim\\organisms.xml";
    Micro_Sim processing;

    //Smelly enum code. If you have any other idea how to do it better, tell me plox.
    enum COMPONENTS {
        Cytoplasm, Driver, Rotator
    }

    enum TRIGGERS {
        EyeTrigger, ChronoTrigger, EdgeTrigger
    }

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;

    XML(Micro_Sim processing) {
        this.processing = processing;
        File file = new File(filepath);
        file.delete();

        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }


    public void SaveOrganism(Organism organism, int round) {
        Document doc = OpenDoc();

        Element rootElement = (Element)doc.getFirstChild();
        NodeList organisms = rootElement.getElementsByTagName("organism");

        Element eOrganism = null;
        boolean found = false;
        for (int i = 0; i < organisms.getLength(); i++) {
            eOrganism = (Element)organisms.item(i);
            if(Integer.parseInt(((Element)organisms.item(i)).getAttribute("id")) == organism.id) {
                found = true;
                break;
            }
        }

        if(!found) {
            eOrganism = doc.createElement("organism");
            eOrganism.setAttribute("id", Integer.toString(organism.id));
            rootElement.appendChild(eOrganism);
        }

        Element grid = doc.createElement("grid");
        grid.setAttribute("iteration", Integer.toString(round));
        eOrganism.appendChild(grid);
        Element size = doc.createElement("size");
        size.appendChild(doc.createTextNode(organism.grid.size.x + "," + organism.grid.size.y));

        Element points = doc.createElement("points");
        points.appendChild(doc.createTextNode(Integer.toString(organism.points)));
        grid.appendChild(points);

        for (int x = 0; x < organism.grid.size.x; x++) {
            for (int y = 0; y < organism.grid.size.y; y++) {
                Element slot = doc.createElement("slot");
                slot.appendChild(ParseComponent((IComponent)organism.grid.contents[y][x], doc)); //error
                slot.setAttribute("x", Integer.toString(x));
                slot.setAttribute("y", Integer.toString(y));
                grid.appendChild(slot);
            }
        }

        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\Ecoste\\IdeaProjects\\MicroSim\\organisms.xml"));

            transformer.transform(source, result);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    Element ParseComponent(IComponent component, Document doc) {
        Element eComponent = null;

        //Smelly code, would like to do it somehow differently. If you know a better way, plox tell me.
        COMPONENTS z = COMPONENTS.valueOf(component.getClass().getSimpleName());
        switch (z) {
            case Driver:
                eComponent = ParseDriver((Driver)component, doc);
                break;
            case Cytoplasm:
                eComponent = ParseCytoplasm((Cytoplasm)component, doc);
                break;
            case Rotator:
                eComponent = ParseRotator((Rotator)component, doc);
                break;
        }
        for (Trigger trigger : component.getTriggers()) {
            eComponent.appendChild(ParseTrigger(trigger, doc));
        }

        return eComponent;
    }

    Element ParseTrigger(Trigger trigger, Document doc) {
        TRIGGERS z = TRIGGERS.valueOf(trigger.getClass().getSimpleName());

        switch (z) {
            case EdgeTrigger:
                return ParseEdgeTrigger((EdgeTrigger)trigger, doc);
            case EyeTrigger:
                return ParseEyeTrigger((EyeTrigger)trigger, doc);
            case ChronoTrigger:
                return ParseChronoTrigger((ChronoTrigger) trigger, doc);
        }

        return null;
    }

    Element ParseCytoplasm(Cytoplasm cytoplasm, Document doc) {
        Element eCytoplasm = doc.createElement("Cytoplasm");
        return eCytoplasm;
    }

    Element ParseDriver(Driver driver, Document doc) {
        Element eDriver = doc.createElement("Driver");

        Element distance = doc.createElement("distance");
        distance.appendChild(doc.createTextNode(Float.toString(driver.distance)));
        eDriver.appendChild(distance);

        return eDriver;
    }

    Element ParseRotator(Rotator rotator, Document doc) {
        Element eRotator = doc.createElement("Rotator");

        Element angle = doc.createElement("angle");
        angle.appendChild(doc.createTextNode(Float.toString(rotator.angle)));
        eRotator.appendChild(angle);

        return eRotator;
    }

    Element ParseChronoTrigger(ChronoTrigger trigger, Document doc) {
        Element eTrigger = doc.createElement("ChronoTrigger");

        Element interval = doc.createElement("interval");
        interval.appendChild(doc.createTextNode(Integer.toString(trigger.interval)));
        eTrigger.appendChild(interval);

        return eTrigger;
    }

    Element ParseEdgeTrigger(EdgeTrigger trigger, Document doc) {
        Element eTrigger = doc.createElement("EdgeTrigger");

        Element cooldown = doc.createElement("cooldown");
        cooldown.appendChild(doc.createTextNode(Float.toString(trigger.coolDownTime)));
        eTrigger.appendChild(cooldown);

        return eTrigger;
    }

    Element ParseEyeTrigger(EyeTrigger trigger, Document doc) {
        Element eTrigger = doc.createElement("EyeTrigger");

        Element cooldown = doc.createElement("cooldown");
        cooldown.appendChild(doc.createTextNode(Float.toString(trigger.coolDownTime)));
        eTrigger.appendChild(cooldown);

        Element radius = doc.createElement("radius");
        radius.appendChild(doc.createTextNode(Float.toString(trigger.radius)));
        eTrigger.appendChild(radius);

        Element dotProductRange = doc.createElement("dotProductRange");
        dotProductRange.appendChild(doc.createTextNode(Float.toString(trigger.dotProductRange)));
        eTrigger.appendChild(dotProductRange);

        eTrigger.appendChild(cooldown);

        return eTrigger;
    }

    public Grid ReadInGrid(int id, int iteration, GameObject parent) {
        Grid grid = new Grid(new Point(7, 7), parent);

        Document doc = OpenDoc();

        NodeList organisms = doc.getElementsByTagName("organism");
        Element organism = null;
        for (int i = 0; i < organisms.getLength(); i++) {
            organism = (Element)organisms.item(i);
            if (Integer.parseInt(organism.getAttribute("id")) == id) {
                break;
            }
        }

        NodeList grids = organism.getElementsByTagName("grid");
        Element eGrid = null;
        for (int i = 0; i < grids.getLength(); i++) {
            eGrid = (Element)grids.item(i);
            //System.out.println(eGrid.getAttribute("iteration"));
            if (Integer.parseInt(eGrid.getAttribute("iteration")) == iteration) {
                break;
            }
        }

        for(int x = 0; x < grid.size.x; x++) {
            for(int y = 0; y < grid.size.y; y++) {
                processing.RemoveGameObject(grid.getContents()[x][y]);
                GameObject component = ReadInComponent(eGrid, x, y);
                grid.contents[x][y] = component;
                component.SetPosition(new Point2D.Float(5 * x, 5 * y));
            }
        }

        return grid;
    }

    Document OpenDoc() {
        Document doc = null;
        try {
            doc = docBuilder.parse(filepath);
        } catch (java.io.IOException e) {
            doc = docBuilder.newDocument();
            doc.appendChild(doc.createElement("organisms"));
        } catch (org.xml.sax.SAXException e) {
            System.out.println(e.getMessage());
        }

        return doc;
    }

    GameObject ReadInComponent(Element grid, int x, int y) {
        NodeList slots = grid.getElementsByTagName("slot");

        Element slot = null;
        for(int i = 0; i < slots.getLength(); i++) {
            slot = (Element)slots.item(i);

            if (Integer.parseInt(slot.getAttribute("x")) == x && Integer.parseInt(slot.getAttribute("y")) == y) {
                break;
            }
        }

        Element eComponent = (Element)slot.getFirstChild();
        GameObject r = null;
        switch (eComponent.getTagName()) {
            case "Driver":
                r = ReadInDriver(eComponent);
                break;
            case "Cytoplasm":
                r = new Cytoplasm(processing);
                break;
            case "Rotator":
                r = ReadInRotator(eComponent);
                break;
        }

        return r;
    }

    GameObject ReadInDriver(Element slot) {
        float distance = Float.parseFloat(slot.getElementsByTagName("distance").item(0).getTextContent());
        Driver driver = new Driver(processing, distance);
        driver.AddTrigger(ReadInTrigger(slot, driver));

        return driver;
    }

    GameObject ReadInRotator(Element slot) {
        float angle = Float.parseFloat(slot.getElementsByTagName("angle").item(0).getTextContent());
        Rotator rotator = new Rotator(processing, angle);
        rotator.AddTrigger(ReadInTrigger(slot, rotator));

        return rotator;
    }

    Trigger ReadInTrigger(Element eComponent, IComponent parent) {
        Element eye = (Element)eComponent.getElementsByTagName("EyeTrigger").item(0);
        Element chrono = (Element)eComponent.getElementsByTagName("ChronoTrigger").item(0);
        Element edge = (Element)eComponent.getElementsByTagName("EdgeTrigger").item(0);

        if(eye != null) {
            return ReadInEyeTrigger(eye, parent);
        }

        if(chrono != null) {
            return ReadInChronoTrigger(chrono, parent);
        }

        if(edge != null) {
            return ReadInEdgeTrigger(edge, parent);
        }

        return null;
    }

    Trigger ReadInEyeTrigger(Element trigger, IComponent parent) {
        float radius = Float.parseFloat(trigger.getElementsByTagName("radius").item(0).getTextContent());
        float dotProductRange = Float.parseFloat(trigger.getElementsByTagName("dotProductRange").item(0).getTextContent());
        float cooldown = Float.parseFloat(trigger.getElementsByTagName("cooldown").item(0).getTextContent());

        return new EyeTrigger(parent, processing, radius, dotProductRange, cooldown);
    }

    Trigger ReadInChronoTrigger(Element trigger, IComponent parent) {
        int interval = Integer.parseInt(trigger.getElementsByTagName("interval").item(0).getTextContent());

        return new ChronoTrigger(parent, processing, interval);
    }

    Trigger ReadInEdgeTrigger(Element trigger, IComponent parent) {
        float cooldown = Float.parseFloat(trigger.getElementsByTagName("cooldown").item(0).getTextContent());

        return new EdgeTrigger(parent, processing, cooldown);
    }
}
