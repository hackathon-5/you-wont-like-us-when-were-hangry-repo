import datetime
from flask import Blueprint, jsonify, request, g
from .. import app, db
from ..models import Pet, Shelter, Reservations

evil_bp = Blueprint('evil', __name__, url_prefix='/evil')

@evil_bp.route('/never_come_here_simba', methods=['POST'])
def nuke_it():
    db.reflect()
    db.drop_all()

    rv = jsonify({'message': 'You have destoyed it you sick bastard.'})
    rv.status_code = 200
    return rv


@evil_bp.route('/dummy_fool_crazy_data', methods=['GET'])
def populate_it():
    times = [900, 930, 1000, 1030, 1100, 1130, 1200, 1230, 1300, 1330, 1400, 1430, 1500, 1530, 1600, 1630, 1700]
    dates = ['2015-08-29', '2015-08-30', '2015-08-31', '2015-09-01', '2015-09-02', '2015-09-03', '2015-09-04']

    shelter1 = Shelter(name='Greyhoud Pets of America - Charleston', description='Greyhound Pets of America - Charleston is an all-volunteer, nonprofit organization dedicated to finding caring, permanent homes for retired racing greyhounds, and to providing support and guidance for the adopted dogs and their families.', address='PO Box 14533 Charleston, SC 29422', latitude=32.897561, longitude=-80.026065)

    shelter2 = Shelter(name="Daisy's Place Retriever Rescue", description="Daisy's Place Retriever Rescue is dedicated to saving and finding loving homes for Retrievers (and Retriever mixes) six years and older.", address='PO Box 20729 Charleston, SC 29413', latitude=32.712772, longitude=-79.965249)

    shelter3 = Shelter(name='Advanced Animal Care', description='Since 2001, Advanced Animal Care has been serving the Mount Pleasant and surrounding areas. We are a full-service veterinary practice offering general veterinary medicine and surgery.', address="3373 S. Morgan's Point Rd. Mt. Pleasant, SC 29466", latitude=32.996595, longitude=-80.039764)

    db.session.add(shelter1)
    db.session.add(shelter2)
    db.session.add(shelter3)
    db.session.flush()

    pets = []

    ethereal = Pet(name='Ethereal Parable', description='Ethereal Parable will be arriving in Charleston on August 30th from Orlando, Fl. He is a handsome black boy who raced at 78 lbs. He is thought to be kitty friendly. He is 3 years old with a birth date of May 14, 2012. We will update his info once he arrives and as we get to know him.', type='dog', age=3.0, cuddle_score=4.5, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/33101919/1/?bust=1440508985&width=632&no_scale_up=1')
    mohican = Pet(name='Mohican Ely', description='Ely is a handsome white and fawn boy who raced at 73 lbs. He is thought to not be kitty friendly.He is 2 1/2 years old with a birth date of March1, 2013. We will update his information once he arrives in Charleston and as we get to know him.', type='dog', age=2.5, cuddle_score=4.1, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/33101892/1/?bust=1440508668&width=632&no_scale_up=1')
    woohoo = Pet(name='Woohoo', description='Woohoo (nicknamed Tina by her foster family) arrived in the lowcountry on July 24th from Jacksonville. She is a pretty red girl who raced at 52 lbs. She was previously in a home but the owner died so she was returned to the kennel.', type='dog', age=3.0, cuddle_score=4.1, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32783321/1/?bust=1437418053&width=632&no_scale_up=1')
    mike = Pet(name='Mike', description='Mike will be arriving in the lowcountry on July 24th from Jacksonville, Fl. He is a handsome brindle boy who raced at 75 lbs. He is 2 years old with a birth date of April 24, 2013. He is kitty friendly. We will update his information as we get to know him.', type='dog', age=2.0, cuddle_score=4.5, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32783282/1/?bust=1437417671&width=632&no_scale_up=1')
    wilbur = Pet(name='Wilbur', description='Wilbur joined our group earlier this year after being in a home for several years. He turned 8 on June 12. He has excellent leash manners and is completely housebroken. He seems to enjoy the company of other greyhounds and will do best with an experienced greyhound owner.', type='dog', age=8.0, cuddle_score=3.8, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/31753425/3/?bust=1433432406&width=632&no_scale_up=1')
    billy = Pet(name='Ballys Bill Mac', description='Billy has recently been returned to the group for no fault of his own. He originally arrived in Charleston on February 20th. He is a handsome red boy with dark eyeliner and muzzle who raced at 70 lbs. He is kitty friendly and gets along well with dogs of all sizes. He is 3 years old with a birth date of April 7, 2012. He has a happy laidback personality.', type='dog', age=3.0, cuddle_score=4.8, shelter=shelter1.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/31445916/1/?bust=1437529148&width=632&no_scale_up=1')

    rose = Pet(name='Rose', description="Hi there! As you cans see, I'm a beautiful black Labby girl and I'm so happy! I'm about 7-8 years young, and I love meeting new people - I've never met a stranger! I get along with everyone including the other dogs and kitties at my foster home - and I really love the little 2 legged types! I also love going for car rides - I'm always up for an adventure!", type='dog', age=7.0, cuddle_score=4.8, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32949670/1/?bust=1438961895&width=632&no_scale_up=1')
    jonah = Pet(name='Jonah', description="Hi! I'm Jonah and as you can see, I'm a handsome yellow Lab! I've got a very thick longer coat, so I might have a little husky or shepherd in my bloodline, too! I'm about 10 years young and I love to run outside - I'll do best in a house with another easy going dog for company", type='dog', age=10.0, cuddle_score=4.9, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32058198/1/?bust=1430575299&width=632&no_scale_up=1')
    artie = Pet(name='Artie', description="Artie is great with dogs, cats, kids, everyone! He's great on walks (up to 1/2 mile at a time). Artie also likes to sit by your side and relax. Apply for Artie today!", type='dog', age=5.5, cuddle_score=5.0, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32858722/1/?bust=1438116232&width=632&no_scale_up=1')
    toby = Pet(name='Toby', description="Meet Toby. Toby is a 9 month old. This guy is great with dogs, but no cats please. Toby is a young and exuberant fellow and would likely be better in a home with older kids. Toby has a love of life and is learning to understand his size and improve his manners. More updates on his training coming soon.", type='dog', age=1.0, cuddle_score=4.2, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32406554/1/?bust=1433975398&width=632&no_scale_up=1')
    jeb = Pet(name='Jeb', description="Meet Jeb. Jeb is 8 years old and working to lose a little weight. He is about 90 pounds. This fellow is good with dogs and cats and older kids. While he loves them all he is looking for a relatively quiet life. Jeb is well mannered. Although he has not been crated he is house trained, good on leash and knows basic commands.", type='dog', age=8.0, cuddle_score=4.9, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/31279172/2/?bust=1421795663&width=632&no_scale_up=1')
    lilly = Pet(name='Lilly', description="Meet Lilly. Lilly is about 4 years old and around 80 pounds. This girl is VERY shy. Lilly is well mannered but a quiet, shy girl learning to trust again. She is house, crate and leash trained. In fact she loves to go on walks. She knows some basic commands. Lilly’s coat seems to be darkening as she gets fitter.", type='dog', age=4.0, cuddle_score=4.5, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/31204757/2/?bust=1420810298&width=632&no_scale_up=1')
    mattie = Pet(name='Mattie', description="Meet Mattie. Mattie is an energetic 8 year old. This girl is great with other dogs and kids, but her view on cats is not yet known. She is house trained and does not need a crate. Mattie is working to improve her leash and basic command skills. Mattie leaps with joy when she sees the leash and loves taking long walks.", type='dog', age=8.0, cuddle_score=3.6, shelter=shelter2.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/31928729/3/?bust=1429137574&width=632&no_scale_up=1')

    cricket = Pet(name='Cricket', description='Cricket is a 3 yr. old female cat that was adopted from us about 2 years ago. Unfortunately, her family could no longer care for her and had to make the heartbreaking decision to return her back to us. She is still incredibly sweet and affectionate, despite having to be relocated back.', type='cat', age=2.0, cuddle_score=4.3, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32919246/2/?bust=1438702789&width=632&no_scale_up=1')
    fiona = Pet(name='Fiona', description="Fiona is the SWEETEST, SMALLEST, CUTEST, thing you've ever seen! She is about 6 weeks old and was bottle raised by one of our technicians here. She is healthy, happy, and well-adjusted, and just wants someone to cuddle up with at night.", type='cat', age=0.1, cuddle_score=5.0, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32950890/1/?bust=1438968703&width=632&no_scale_up=1')
    lolly = Pet(name='Lolly', description='Lolly is 2 years old and weighs about 7 pounds. She is waiting patiently for a loving home. She tested negative for feline leukemia, has been dewormed, received the FVRCP and Rabies Vaccines, and has been spayed.', type='cat', age=2.0, cuddle_score=3.2, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/33011543/1/?bust=1439569551&width=632&no_scale_up=1')
    moby = Pet(name='Moby', description='About 3 years old. Good with cats and dogs. "Talks" to you for attention and is very sweet. Come spend some time with him if you have a chance!', type='cat', age=3.0, cuddle_score=4.0, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/32506347/1/?bust=1434913182&width=632&no_scale_up=1')
    milo = Pet(name='Milo', description='My name is Milo. I was named after a hobbit from Lord of the Rings. I am a brown tabby cat but not an ordinary tabby. I have the biggest eyes, the most handsome face and a white bib and feet. I am easy going and grateful for any attention. I purr, reach for your touch and love to sit in your lap. I am a very good boy who deserves a loving stable forever home.', type='cat', age=4.5, cuddle_score=2.8, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/27024593/2/?bust=1384104128&width=632&no_scale_up=1')
    swenor = Pet(name='Sweden & Norway', description='Sweden and Norway are so close they wanted to have their picture taken together. They are almost 3 months old.', type='cat', age=0.3, cuddle_score=2.5, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/33061353/1/?bust=1440081467&width=632&no_scale_up=1')
    chive = Pet(name='Chive', description='Chive is a very alert, funny, and playful 10 month old kitty. She is fun to watch and will take a break to give you some affection while she catches her breath', type='cat', age=0.8, cuddle_score=1.5, shelter=shelter3.id, photoUrl='https://drpem3xzef3kf.cloudfront.net/photos/pets/16088332/2/?bust=1300631273&width=632&no_scale_up=1')

    pets += ethereal
    pets += rose
    pets += cricket
    pets += mohican
    pets += woohoo
    pets += mike
    pets += wilbur
    pets += billy
    pets += jonah
    pets += artie
    pets += toby
    pets += jeb
    pets += lilly
    pets += mattie
    pets += fiona
    pets += lolly
    pets += moby
    pets += moby
    pets += milo
    pets += swenor
    pets += chive

    for pet in pets:
        db.session.add(pet)

    db.session.flush()

    shelter1.pets.append(ethereal)
    shelter1.pets.append(mohican)
    shelter1.pets.append(woohoo)
    shelter1.pets.append(mike)
    shelter1.pets.append(wilbur)
    shelter1.pets.append(billy)

    shelter2.pets.append(rose)
    shelter2.pets.append(jonah)
    shelter2.pets.append(artie)
    shelter2.pets.append(toby)
    shelter2.pets.append(jeb)
    shelter2.pets.append(lilly)
    shelter2.pets.append(mattie)

    shelter3.pets.append(cricket)
    shelter3.pets.append(fiona)
    shelter3.pets.append(lolly)
    shelter3.pets.append(moby)
    shelter3.pets.append(milo)
    shelter3.pets.append(swenor)
    shelter3.pets.append(chive)

    size = len(times)
    reservations = []
    while size:
        size = size - 1
        index = whrandom.randint(0, size)
        reservations += Reservations(date=random.choice(dates), times=times[index], pet_id=random.choice(pets), user_id=2174488375)
        times[index] = times[size]

    user = User.query.get(2174488375)

    for reservation in reservations:
        db.session.add(reservation)
        user.reservations.append(reservation)

    db.session.flush()

    db.session.commit()
