/**
 * Created by Ecoste on 2/6/2017.
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.sun.javafx.geom.Edge;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XML {
    //Smelly enum code. If you have any other idea how to do it better, tell me plox.
    enum CLAZZ {
        Cytoplasm, Driver, Rotator;
    }

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;

    XML() {
        docFactory = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }


    public void SaveOrganism(Organism organism) {
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("organisms");
        doc.appendChild(rootElement);

        Element eOrganism = doc.createElement("organism");
        eOrganism.setAttribute("id", Integer.toString(organism.id));
        rootElement.appendChild(eOrganism);

        Element grid = doc.createElement("grid");
        grid.setAttribute("iteration", "0");
        eOrganism.appendChild(grid);
        Element size = doc.createElement("size");
        size.appendChild(doc.createTextNode(organism.grid.size.x + "," + organism.grid.size.y));

        for (int x = 0; x < organism.grid.size.x; x++) {
            for (int y = 0; y < organism.grid.size.y; y++) {
                Element slot = doc.createElement("slot");
                slot.appendChild(ParseComponent((IComponent)organism.grid.contents.get(y).get(x), doc));
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
        //Smelly code, would like to do it somehow differently. If you know a better way, plox tell me.
        System.out.println(component.getClass().getSimpleName());
        CLAZZ z = CLAZZ.valueOf(component.getClass().getSimpleName());
        switch (z) {
            case Driver:
                return ParseDriver((Driver)component, doc);
            case Cytoplasm:
                return ParseCytoplasm((Cytoplasm)component, doc);
            case Rotator:
                return ParseRotator((Rotator)component, doc);
        }

        System.out.println("derp");
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
        eTrigger.appendChild(eTrigger);

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
}
