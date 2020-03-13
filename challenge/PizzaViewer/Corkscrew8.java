public class Corkscrew8 implements Solver
{
    PointP[] route;

    public PointP[] computeRoute(PointP[] cities)
    {
        route = new PointP[cities.length];

        for (int i = 0; i < cities.length; i++)
        {
            route[i] = cities[i];
        }

        float oudehoek, nieuwehoek, referentiehoek;
        PointP last = new PointP(0, 1);
        PointP current = route[0];
        PointP end = new PointP(0, 0);
        referentiehoek = berekenhoek(last, current, end);

        int laatsteindex = 0;

        for (int i = 1; i < route.length; i++)
        {
            oudehoek = berekenhoek(last, current, route[i]);

            for (int j = i + 1; j < route.length; j++)
            {
                nieuwehoek = berekenhoek(last, current, route[j]);

                if (nieuwehoek < oudehoek)
                {
                    swap(i, j);
                    oudehoek = nieuwehoek;
                }
                else
                {
                    if (nieuwehoek == oudehoek)
                    {
                        if (PointP.distance(current, route[j]) < PointP.distance(current, route[i]))
                        {
                            swap(i, j);
                        }
                    }
                }
            }

            if (referentiehoek < oudehoek)
            {
                break;
            }

            laatsteindex++;
            last = route[i - 1];
            current = route[i];
            referentiehoek = berekenhoek(last, current, end);
        }

        float verschiloud, verschilnieuw;
        PointP punteen, punttwee;
        
        for (int i = laatsteindex + 1; i < route.length; i++)
        {
            verschiloud = 1000000;
            int minpuntindex = -1;

            for (int k = 0; k < i; k++)
            {
                if ((k + 1) == i)
                {
                    punteen = route[k];
                    punttwee = route[0];
                }
                else
                {
                    punteen = route[k];
                    punttwee = route[k + 1];
                }

                PointP zoekpunt = route[i];
                verschilnieuw = binnenstepunt(punteen, punttwee, zoekpunt);

                if (verschilnieuw < verschiloud)
                {
                    verschiloud = verschilnieuw;
                    minpuntindex = k + 1;
                }
            }

            for (int n = i; n > minpuntindex; n--)
            {
                swap(n, n - 1);
            }
        }

        return route;
    }

    public float berekenhoek(PointP last, PointP current, PointP next)
    {
        float a, b, c, nieuwehoek;

        a = PointP.distance(last, current);
        b = PointP.distance(current, next);
        c = PointP.distance(last, next);
        nieuwehoek = (((a * a) + (b * b)) - (c * c)) / (2 * a * b);

        return nieuwehoek;
    }

    public float binnenstepunt(PointP punteen, PointP punttwee, PointP zoekpunt)
    {
        float d, e, f, binnenste;

        d = PointP.distance(punteen, zoekpunt);
        e = PointP.distance(punttwee, zoekpunt);
        f = PointP.distance(punteen, punttwee);
        binnenste = ((d + e) - f);

        return binnenste;
    }

    private void swap(int j, int i)
    {
        PointP h;
        h = route[j];
        route[j] = route[i];
        route[i] = h;
    }

    public String getAuthors()
    {
        return "Ruud & Peter";
    }

    public String getDescription()
    {
        return "Corkscrew Convection v1.0";
    }

    public static void main(String[] args)
    {
        Solver solver = new Corkscrew8();
        new PizzaViewer(solver);
    }
}
