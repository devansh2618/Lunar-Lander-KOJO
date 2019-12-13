//download the images and change their paths in kojo.
//to download images go to the github repository - ""

cleari()
drawStage(noColor)
val cb = canvasBounds
var bg = scale(2.7, 2.95) -> Picture.image("sky2.png")
bg.setPosition(cb.x, cb.y)
bg.setOpacity(0.6)
draw(bg)

class Lander {
    val rocket = scale(0.15, 0.15) -> Picture.image("imgrocket.png")
    rocket.setPosition(-30, 60)
    val thruster1 = scale(0.04, 0.1) -> Picture.image("imgfire.png")
    thruster1.setPosition(rocket.position.x + 40, rocket.position.y - 25)
    val thruster2 = scale(0.04, 0.1) -> Picture.image("imgfire.png")
    thruster2.setPosition(rocket.position.x, rocket.position.y - 25)

    def draw() {
        thruster1.draw()
        thruster2.draw()
        rocket.draw()
        thruster1.invisible()
        thruster2.invisible()
    }

    var vel = Vector2D(0, 0)
    val gravity = Vector2D(0, -0.4)

    def inThrust() {
        thruster1.visible()
        thruster1.setPosition(rocket.position.x + 40, rocket.position.y - 25)
        thruster2.visible()
        thruster2.setPosition(rocket.position.x + 10, rocket.position.y - 25)
    }

    def noThrust() {
        thruster1.invisible()
        thruster1.setPosition(rocket.position.x + 40, rocket.position.y - 25)
        thruster2.invisible()
        thruster2.setPosition(rocket.position.x + 10, rocket.position.y - 25)
    }

    def step() {
        if (isKeyPressed(Kc.VK_UP)) {
            inThrust()
            vel = vel - (gravity * 1.5)
            rocket.translate(vel)
        }

        else {
            noThrust()
            vel = vel + gravity
            rocket.translate(vel)
        }

        if (rocket.collidesWith(stageTop)) {
            vel = vel + (gravity * 10)
            rocket.translate(vel)
        }
    }

    def check() {
        if (rocket.collidesWith(m.moon)) {
            if (vel.y > -5.0) {
                rocket.setOpacity(0.6)
                thruster1.setOpacity(0.6)
                thruster2.setOpacity(0.6)
                drawCenteredMessage("Perfect! You Win.", yellow, 30)
                stopAnimation()
                thruster1.visible()
                thruster2.visible()
                m.crashedMoon.visible()
                m.moon.invisible()
            }

            else if (vel.y <= -5.0) {
                rocket.setOpacity(0.6)
                thruster1.setOpacity(0.6)
                thruster2.setOpacity(0.6)
                drawCenteredMessage("Crash Landing! Slow down your speed.", red, 30)
                stopAnimation()
                thruster1.visible()
                thruster2.visible()
                m.crashedMoon.visible()
                m.moon.invisible()
            }
        }
    }
}

class Moon {
    val moon = scale(0.6, 0.6) -> Picture.image("moon.png")
    val crashedMoon = scale(0.6, 0.6) -> Picture.image("crashedMoon.png")

    moon.setPosition(cb.x + 250, cb.y - 260)
    crashedMoon.setPosition(cb.x + 250, cb.y - 260)

    def draw() {
        moon.draw()
        crashedMoon.draw()
        crashedMoon.invisible()
    }
}

val m = new Moon
m.draw()
val l = new Lander
l.draw()

animate {
    l.step()
    l.check()
}

activateCanvas()